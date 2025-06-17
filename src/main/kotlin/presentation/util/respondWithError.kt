package org.aprikot.presentation.util

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import org.aprikot.domain.util.DataError

suspend fun RoutingContext.respondWithError (
    error: DataError
){
    when (error) {
        DataError.Database -> {
            call.respond(
                message = "Database error occured",
                status = HttpStatusCode.InternalServerError
            )
        }
        DataError.NotFound -> {
            call.respond(
                message = "Resource not Found",
                status = HttpStatusCode.NotFound
            )
        }
        DataError.Unknown -> {
            call.respond(
                message = "An unknown error occured",
                status = HttpStatusCode.InternalServerError
            )
        }
        DataError.Validation -> {
            call.respond(
                message = "Invalid data provided",
                status = HttpStatusCode.BadRequest
            )
        }
    }
}