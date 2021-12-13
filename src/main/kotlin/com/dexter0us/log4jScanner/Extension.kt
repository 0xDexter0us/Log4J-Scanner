package com.dexter0us.log4jScanner

import burp.IBurpExtender
import burp.IBurpExtenderCallbacks
import burp.IExtensionStateListener
import burp.ITab
import java.awt.Component
import java.io.PrintWriter

open class Extension: IBurpExtender, IExtensionStateListener {

    companion object{
        const val pluginName = "Log4J Scanner"
        const val version = "0.1.0"
    }

    override fun registerExtenderCallbacks(_callbacks: IBurpExtenderCallbacks) {
        callbacks = _callbacks
        helpers = _callbacks.helpers
        stdout = PrintWriter(callbacks.stdout, true)
        stderr = PrintWriter(callbacks.stderr, true)

        callbacks.apply {
            setExtensionName(pluginName)
            addSuiteTab(UI())
            registerExtensionStateListener { extensionUnloaded() }
        }

        console("$pluginName v$version Loaded")

    }

    override fun extensionUnloaded() {
        currJob?.cancel()
        console("Log4J scanner unloaded.")
    }

    private inner class UI : ITab{
        override fun getTabCaption(): String = "Log4J Scanner"
        override fun getUiComponent(): Component = Log4JTab()
    }

}