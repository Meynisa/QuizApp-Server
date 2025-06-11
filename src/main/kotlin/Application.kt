package org.aprikot

import io.ktor.server.application.*
import org.aprikot.presentation.config.configureLogging
import org.aprikot.presentation.config.configureRouting
import org.aprikot.presentation.config.configureSerialization


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureLogging()
    configureSerialization()
    configureRouting()
}
