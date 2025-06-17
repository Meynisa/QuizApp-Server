package org.aprikot.presentation.routes.quiz_topic

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.aprikot.domain.model.QuizTopic
import org.aprikot.domain.repository.QuizTopicRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.upsertMultipleTopics(
    quizTopicRepository: QuizTopicRepository
) {
    post<QuizTopicRoutesPath.Batch> {

        val listOfTopics = call.receive<List<QuizTopic>>()

        quizTopicRepository.upsertMultipleTopic(listOfTopics)
            .onSuccess {
                call.respond(
                    message = "${listOfTopics.size} Topics added successfully",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}