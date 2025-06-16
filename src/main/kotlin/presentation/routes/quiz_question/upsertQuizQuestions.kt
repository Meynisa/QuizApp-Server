package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.upsertQuizQuestions(
    quizQuestionRepository: QuizQuestionRepository
){
    post<QuizQuestionRoutesPath>{
        val question = call.receive<QuizQuestion>()

        quizQuestionRepository.upsertQuestion(question)
            .onSuccess {
                call.respond(
                    message = "Question added successfully",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}