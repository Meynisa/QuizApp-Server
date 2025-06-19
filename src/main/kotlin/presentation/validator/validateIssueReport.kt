package org.aprikot.presentation.validator

import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import org.aprikot.domain.model.IssueReport

fun RequestValidationConfig.validationIssueReport() {
    validate<IssueReport> { report ->

        val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")

        when {
            report.questionId.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Question ID must not be empty"
                )
            }
            report.issueType.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Issue type must not be empty"
                )
            }
            report.timestamp.isBlank() -> {
                ValidationResult.Invalid(
                    reason = "Timestamp must not be empty"
                )
            }
            report.additionalComment != null && report.additionalComment.length < 5 -> {
                ValidationResult.Invalid(
                    reason = "Additional comment must be at least 5 characters long"
                )
            }
            report.userEmail != null && !report.userEmail.matches(emailRegex) -> {
                ValidationResult.Invalid(
                    reason = "Invalid email format"
                )
            }
            else -> {
                ValidationResult.Valid
            }
        }
    }
}