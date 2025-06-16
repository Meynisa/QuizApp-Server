package org.aprikot.presentation.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import org.aprikot.presentation.validator.validationQuizQuestion

fun Application.configureValidation() {
    install(RequestValidation){
        validationQuizQuestion()
    }
}