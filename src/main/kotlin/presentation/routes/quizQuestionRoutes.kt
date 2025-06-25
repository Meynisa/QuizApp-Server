package presentation.routes

import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import presentation.routes.path.QuizQuestionRoutesPath
import org.aprikot.presentation.util.respondWithError

fun Route.quizQuestionRoutes(
    quizQuestionRepository: QuizQuestionRepository
) {
    delete<QuizQuestionRoutesPath.ById> { path ->
        quizQuestionRepository.deleteQuestionById(path.questionId)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

    authenticate {
        get<QuizQuestionRoutesPath> { path ->
            quizQuestionRepository.getAllQuestions(path.topicCode, path.limit)
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

    authenticate {
        get<QuizQuestionRoutesPath.ById> { path ->
            quizQuestionRepository.getQuestionById(path.questionId)
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

    authenticate {
        get<QuizQuestionRoutesPath.Random> { path ->
            quizQuestionRepository.getRandomQuestions(path.topicCode, path.limit)
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

    post<QuizQuestionRoutesPath.Batch> {

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

    authenticate {
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
}