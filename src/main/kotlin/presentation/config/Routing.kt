package org.aprikot.presentation.config

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.presentation.routes.quiz_question.deleteQuizQuestionById
import org.aprikot.presentation.routes.quiz_question.getAllQuizQuestions
import org.aprikot.presentation.routes.quiz_question.getQuizQuestionById
import org.aprikot.presentation.routes.quiz_question.upserQuizQuestions
import org.aprikot.presentation.routes.root

fun Application.configureRouting() {
    routing {
        root()
        getAllQuizQuestions()
        upserQuizQuestions()
        deleteQuizQuestionById()
        getQuizQuestionById()
    }
}

val quizQuestions = mutableListOf<QuizQuestion>()