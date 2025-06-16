package org.aprikot.presentation.validator

import io.ktor.server.plugins.requestvalidation.*
import org.aprikot.domain.model.QuizQuestion

fun RequestValidationConfig.validationQuizQuestion() {
    validate<QuizQuestion> { quizQuestion ->
        when {
            quizQuestion.question.isBlank() || quizQuestion.question.length < 5 -> {
                ValidationResult.Invalid(
                    reason = "Please input the correct question"
                )
            }
            quizQuestion.correctAnswer.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Correct answer must not be empty"
                )
            }
            quizQuestion.incorrectAnswers.isEmpty() -> {
                ValidationResult.Invalid(
                    reason = "There must be at least one incorrect empty"
                )
            }
            quizQuestion.incorrectAnswers.any { it.isBlank() } -> {
                ValidationResult.Invalid(
                    reason = "Incorrect answer must not be empty"
                )
            }
            quizQuestion.explanation.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Explanation must not be empty"
                )
            }
            quizQuestion.topicCode <= 0 -> {
                ValidationResult.Invalid(
                    reason = "Topic Code must be a positive integer"
                )
            }
            else -> {
                ValidationResult.Valid
            }
        }
    }
}