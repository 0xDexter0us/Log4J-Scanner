package com.dexter0us.log4jScanner

import burp.*
import java.net.URL
import kotlin.collections.ArrayList


class Log4JScanner: IScannerCheck {

    private val collaborator = callbacks.createBurpCollaboratorClientContext()

    override fun doActiveScan(
        baseRequestResponse: IHttpRequestResponse?, insertionPoint: IScannerInsertionPoint?
    ): MutableList<IScanIssue>? {


        val request = insertionPoint!!.buildRequest(payload())
        val response = callbacks.makeHttpRequest(baseRequestResponse!!.httpService, request)

        val interactions = collaborator.fetchAllCollaboratorInteractions()
        val issues = ArrayList<IScanIssue>()
        return when {
            interactions.size > 0 -> {
                issues.add(
                    ScanIssue(
                        helpers.analyzeRequest(response).url,
                        baseRequestResponse.httpService,
                        arrayOf(response)
                    )
                )
                issues
            }
            else -> null
        }
    }


    override fun consolidateDuplicateIssues(existingIssue: IScanIssue, newIssue: IScanIssue): Int = 0

    override fun doPassiveScan(baseRequestResponse: IHttpRequestResponse?): MutableList<IScanIssue>? = null


    private fun payload(): ByteArray {
        return initialPayload.replace(
            "[collaborator-server]",
            collaborator.generatePayload(true)
        ).toByteArray()
    }

    private inner class ScanIssue(
        private var url: URL?,
        private var httpService: IHttpService?,
        private var httpMessages: Array<IHttpRequestResponse>
    ): IScanIssue {

        override fun getUrl(): URL? =  url

        override fun getIssueName(): String = "Log4J [CVE-2021-44228] Callback"

        override fun getIssueType(): Int = 0

        override fun getSeverity(): String = "High"

        override fun getConfidence(): String= "Certain"

        override fun getIssueBackground(): String? = null

        override fun getRemediationBackground(): String? = null

        override fun getIssueDetail(): String = "Received a ping-back from the application to a Log4J [CVE-2021-44228] payload.<br/>" +
                "Further manual investigation is required, please refer to https://www.lunasec.io/docs/blog/log4j-zero-day/"

        override fun getRemediationDetail(): String? = null

        override fun getHttpMessages(): Array<IHttpRequestResponse> = httpMessages

        override fun getHttpService(): IHttpService? = httpService

    }
}
