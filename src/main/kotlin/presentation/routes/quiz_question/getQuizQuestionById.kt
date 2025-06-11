package org.aprikot.presentation.routes.quiz_question

import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.presentation.config.quizQuestions

fun Route.getQuizQuestionById(){
    get(path = "/quiz/questions/{questionId}"){
        val id = call.parameters["questionId"]
        val question: QuizQuestion? = quizQuestions.find { it.id == id }
        if (question != null) {
            call.respond(question)
        } else{
            call.respondText("Quiz Question not available")
        }
    }
}