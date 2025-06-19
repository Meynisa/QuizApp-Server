package org.aprikot.presentation

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import org.aprikot.presentation.config.configureKoin
import org.aprikot.presentation.config.configureLogging
import org.aprikot.presentation.config.configureRouting
import org.aprikot.presentation.config.configureSerialization
import org.aprikot.presentation.config.configureStatusPages
import org.aprikot.presentation.config.configureValidation
import presentation.config.configureSecurity


fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureKoin()

    configureSecurity()
    configureLogging()
    configureSerialization()
    configureRouting()
    configureValidation()
    configureStatusPages()
}
