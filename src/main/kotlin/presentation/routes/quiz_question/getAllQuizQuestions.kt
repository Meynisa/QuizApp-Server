package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.getAllQuizQuestions(
    quizQuestionRepository: QuizQuestionRepository
){
    get(path = "/quiz/questions"){

        val topicCode = call.queryParameters["topicCode"]?.toIntOrNull()
        val limit = call.queryParameters["limit"]?.toIntOrNull()

        quizQuestionRepository.getAllQuestions(topicCode, limit)
            .onSuccess { questions ->
                call.respond(
                    message = questions,
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}