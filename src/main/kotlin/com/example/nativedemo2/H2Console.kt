package com.example.nativedemo2

import org.h2.tools.Server
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

/**
 * Sample component to run an H2 Console on another port because we're using WebFlux.
 */
@Component
class H2Console : InitializingBean, DisposableBean {
    private var webServer: Server? = null

    override fun afterPropertiesSet() {
        val externalNames = "localhost,127.0.0.1,::1,192.168.64.4"
        webServer = Server.createWebServer(
            "-webPort", "8082",
            "-webAllowOthers", "-webExternalNames", externalNames
        ).start()
        log.info("H2 console available on port 8082 to {}", externalNames)
    }

    override fun destroy() {
        webServer?.stop()
    }

    private companion object {
        @JvmStatic
        private val log: Logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }
}
