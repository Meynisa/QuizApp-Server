package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.repository.QuizQuestionRepository

fun Route.upsertMultipleQuestions(
    quizQuestionRepository: QuizQuestionRepository
){
    post(path = "quiz/multiple-questions"){
        val listOfQuestions = call.receive<List<QuizQuestion>>()
        quizQuestionRepository.upsertMultipleQuestions(listOfQuestions)
        call.respond(
            message = "${listOfQuestions.size} Questions added successfully",
            status = HttpStatusCode.Created
        )
    }
}