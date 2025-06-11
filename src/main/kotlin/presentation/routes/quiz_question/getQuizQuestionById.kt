package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.aprikot.domain.repository.QuizQuestionRepository

fun Route.getQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
){
    get(path = "/quiz/questions/{questionId}"){
        val id = call.parameters["questionId"]
        if (id.isNullOrBlank()){
            call.respond(
                message = "Question ID is needed",
                status = HttpStatusCode.BadRequest
            )
            return@get
        }

        val question = quizQuestionRepository.getQuestionById(id)

        if (question != null) {
            call.respond(
                message = question,
                status = HttpStatusCode.OK
            )
        } else{
            call.respond(
                message = "No Quiz Question",
                status = HttpStatusCode.NotFound
            )
        }
    }
}