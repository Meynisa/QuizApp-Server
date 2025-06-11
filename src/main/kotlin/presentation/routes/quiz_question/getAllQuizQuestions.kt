package org.aprikot.presentation.routes.quiz_question

import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.presentation.config.quizQuestions

fun Route.getAllQuizQuestions(){
    get(path = "/quiz/questions"){
        val topicCode = call.queryParameters["topicCode"]?.toIntOrNull()
        val limit = call.queryParameters["limit"]?.toIntOrNull()
        val filteredQuestions = quizQuestions
            .filter { it.topicCode == topicCode }
            .take(limit ?: 1)

        call.respond(filteredQuestions)
    }
}