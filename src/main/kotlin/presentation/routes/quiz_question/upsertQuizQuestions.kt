package org.aprikot.presentation.routes.quiz_question

import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.presentation.config.quizQuestions

fun Route.upserQuizQuestions(){
    post(path = "quiz/questions"){
        val question = call.receive<QuizQuestion>()
        quizQuestions.add(question)
        call.respondText("Question added successfully!")
    }
}