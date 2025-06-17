package org.aprikot.presentation.routes.quiz_topic

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.aprikot.domain.repository.QuizTopicRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.getAllQuizTopics(
    quizTopicRepository: QuizTopicRepository
) {
    get<QuizTopicRoutesPath> {
        quizTopicRepository.getAllTopics()
            .onSuccess { topics ->
                call.respond(
                    message = topics,
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}