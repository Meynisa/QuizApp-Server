package org.aprikot.presentation.routes.quiz_topic

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.aprikot.domain.repository.QuizTopicRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.deleteQuizTopicById(
    quizTopicRepository: QuizTopicRepository
) {
    delete<QuizTopicRoutesPath.ById> { path ->
        quizTopicRepository.deleteTopicById(path.topicId)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}