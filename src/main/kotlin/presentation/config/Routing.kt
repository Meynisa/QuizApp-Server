package org.aprikot.presentation.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import org.aprikot.data.database.DatabaseFactory
import org.aprikot.data.repository.QuizQuestionRepositoryImpl
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.presentation.routes.quiz_question.deleteQuizQuestionById
import org.aprikot.presentation.routes.quiz_question.getAllQuizQuestions
import org.aprikot.presentation.routes.quiz_question.getQuizQuestionById
import org.aprikot.presentation.routes.quiz_question.upsertQuizQuestions
import org.aprikot.presentation.routes.quiz_question.upsertMultipleQuestions
import org.aprikot.presentation.routes.root

fun Application.configureRouting() {

    install(Resources)

    val mongoDatabase = DatabaseFactory.create()
    val quizQuestionRepository: QuizQuestionRepository = QuizQuestionRepositoryImpl(mongoDatabase)

    routing {

        root()

        getAllQuizQuestions(quizQuestionRepository)
        upsertQuizQuestions(quizQuestionRepository)
        upsertMultipleQuestions(quizQuestionRepository)
        deleteQuizQuestionById(quizQuestionRepository)
        getQuizQuestionById(quizQuestionRepository)
    }
}