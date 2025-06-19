package presentation.validator

import domain.model.User
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validationUser() {
    validate<User> { userData ->

//        val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")

        when {
            userData.username.isBlank() || userData.username.length < 3 -> {
                ValidationResult.Invalid(
                    reason = "Username must be at least 3 characters long"
                )
            }
            userData.password.isBlank() || userData.password.length != 8 -> {
                ValidationResult.Invalid(
                    reason = "Password must be 8 long characters"
                )
            }

            else -> {
                ValidationResult.Valid
            }
        }
    }
}