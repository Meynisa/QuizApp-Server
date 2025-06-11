package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.aprikot.domain.repository.QuizQuestionRepository

fun Route.getAllQuizQuestions(
    quizQuestionRepository: QuizQuestionRepository
){
    get(path = "/quiz/questions"){
        val topicCode = call.queryParameters["topicCode"]?.toIntOrNull()
        val limit = call.queryParameters["limit"]?.toIntOrNull()

        val filteredQuestions = quizQuestionRepository.getAllQuestions(topicCode, limit)
        if (filteredQuestions.isNotEmpty()){
            call.respond(
                message = filteredQuestions,
                status = HttpStatusCode.OK
            )
        }else{
            call.respond(
                message = "No QuizQuestions",
                status = HttpStatusCode.NotFound
            )
        }
    }
}