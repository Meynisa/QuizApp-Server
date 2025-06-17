package org.aprikot.presentation.validator

import io.ktor.server.plugins.requestvalidation.*
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.model.QuizTopic

fun RequestValidationConfig.validationQuizTopic() {
    validate<QuizTopic> { quizTopic ->
        when {
            quizTopic.name.isBlank() || quizTopic.name.length < 3 -> {
                ValidationResult.Invalid(
                    reason = "Please input the correct name"
                )
            }
            quizTopic.imageUrl.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Image url must not be empty"
                )
            }
            quizTopic.code < 0 -> {
                ValidationResult.Invalid(
                    reason = "Topic Code must be a whole number"
                )
            }
            else -> {
                ValidationResult.Valid
            }
        }
    }
}