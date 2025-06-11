package org.aprikot.presentation.routes.quiz_question

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import org.aprikot.presentation.config.quizQuestions

fun Route.deleteQuizQuestionById(){
    delete(path = "/quiz/questions/{questionId}"){
        val id = call.parameters["questionId"]
        quizQuestions.removeIf { it.id == id }
        call.respondText("Quiz Question deleted successfully!")
    }
}