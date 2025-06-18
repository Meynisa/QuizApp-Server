package org.aprikot

import io.ktor.server.application.*
import org.aprikot.presentation.config.configureKoin
import org.aprikot.presentation.config.configureLogging
import org.aprikot.presentation.config.configureRouting
import org.aprikot.presentation.config.configureSerialization
import org.aprikot.presentation.config.configureStatusPages
import org.aprikot.presentation.config.configureValidation


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureLogging()
    configureSerialization()
    configureRouting()
    configureValidation()
    configureStatusPages()
}
