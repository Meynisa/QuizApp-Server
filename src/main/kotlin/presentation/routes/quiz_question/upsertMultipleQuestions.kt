package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError
import io.ktor.server.resources.*

fun Route.upsertMultipleQuestions(
    quizQuestionRepository: QuizQuestionRepository
){
    post<QuizQuestionRoutesPath> {

        val listOfQuestions = call.receive<List<QuizQuestion>>()

        quizQuestionRepository.upsertMultipleQuestions(listOfQuestions)
            .onSuccess {
                call.respond(
                    message = "${listOfQuestions.size} Questions added successfully",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}