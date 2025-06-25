package org.aprikot.presentation.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import org.aprikot.presentation.validator.validationIssueReport
import org.aprikot.presentation.validator.validationQuizQuestion
import org.aprikot.presentation.validator.validationQuizTopic
import presentation.validator.validationUser

fun Application.configureValidation() {
    install(RequestValidation){
        validationQuizQuestion()
        validationQuizTopic()
        validationIssueReport()
        validationUser()
    }
}