package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.repository.QuizQuestionRepository

fun Route.upserQuizQuestions(
    quizQuestionRepository: QuizQuestionRepository
){
    post(path = "quiz/questions"){
        val question = call.receive<QuizQuestion>()
        quizQuestionRepository.upsertQuestion(question)
        call.respond(
            message = "Question added successfully",
            status = HttpStatusCode.Created
        )
    }
}