package presentation.routes

import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.aprikot.domain.model.QuizTopic
import org.aprikot.domain.repository.QuizTopicRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import presentation.routes.path.QuizTopicRoutesPath
import org.aprikot.presentation.util.respondWithError

fun Route.quizTopicRoutes(
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

    authenticate {
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

    authenticate {
        get<QuizTopicRoutesPath.ById> { path ->
            quizTopicRepository.getTopicById(path.topicId)
                .onSuccess { question ->
                    call.respond(
                        message = question,
                        status = HttpStatusCode.OK
                    )
                }
                .onFailure { error ->
                    respondWithError(error)
                }
        }
    }

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

    authenticate {
        post<QuizTopicRoutesPath>{
            val topic = call.receive<QuizTopic>()

            quizTopicRepository.upsertTopic(topic)
                .onSuccess {
                    call.respond(
                        message = "Topic added successfully",
                        status = HttpStatusCode.Created
                    )
                }
                .onFailure { error ->
                    respondWithError(error)
                }
        }
    }
}