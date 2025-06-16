package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.deleteQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
){
    delete(path = "/quiz/questions/{questionId}"){

        val id = call.parameters["questionId"]

        quizQuestionRepository.deleteQuestionById(id)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}