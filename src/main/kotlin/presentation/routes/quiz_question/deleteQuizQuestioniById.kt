package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import org.aprikot.domain.repository.QuizQuestionRepository

fun Route.deleteQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
){
    delete(path = "/quiz/questions/{questionId}"){
        val id = call.parameters["questionId"]
        if (id.isNullOrBlank()){
            call.respond(
                message = "Question ID is needed",
                status = HttpStatusCode.BadRequest
            )
            return@delete
        }
        val isDeleted = quizQuestionRepository.deleteQuestionById(id)
        if (isDeleted){
            call.respond(HttpStatusCode.NoContent)
        }else{
            call.respond(
                message = "No Question to delete",
                status = HttpStatusCode.NotFound
            )
        }
    }
}