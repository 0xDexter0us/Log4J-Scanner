package com.dexter0us.log4jScanner

import burp.IBurpExtender
import burp.IBurpExtenderCallbacks
import burp.IExtensionStateListener
import java.io.PrintWriter
import javax.swing.*

open class Extension: IBurpExtender, IExtensionStateListener {

    companion object{
        const val pluginName = "Log4J Scanner"
        const val version = "0.2.0"
    }

    private var scavUnload = false
    private var burpMenu: JMenuBar? = null
    private var log4jMenu: JMenu? = null

    override fun registerExtenderCallbacks(_callbacks: IBurpExtenderCallbacks) {
        callbacks = _callbacks
        helpers = _callbacks.helpers
        stdout = PrintWriter(callbacks.stdout, true)
        stderr = PrintWriter(callbacks.stderr, true)

        callbacks.apply {
            setExtensionName(pluginName)
            registerScannerCheck(Log4JScanner())
            registerExtensionStateListener { extensionUnloaded() }
        }

        stdout.println("$pluginName v$version Loaded")

        SwingUtilities.invokeLater {
            try {
                burpMenu = getBurpFrame()!!.jMenuBar
                log4jMenu = JMenu("Log4J Scanner")
                val listCustomTagsMenu = JMenuItem("Settings")
                listCustomTagsMenu.addActionListener { Log4JUI() }
                log4jMenu!!.add(listCustomTagsMenu)
                burpMenu!!.add(log4jMenu)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    private fun getBurpFrame(): JFrame? {
        for (frame in JFrame.getFrames()) {
            if (frame.isVisible && frame.title.startsWith("Burp Suite")) {
                return frame as JFrame?
            }
        }
        return null
    }

    override fun extensionUnloaded() {
        currJob?.cancel()
        stdout.println("Log4J scanner unloaded.")
        scavUnload = true
        burpMenu?.remove(log4jMenu)
        burpMenu?.repaint()
    }
}