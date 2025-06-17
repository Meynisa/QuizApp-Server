package org.aprikot.presentation.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import org.aprikot.data.database.DatabaseFactory
import org.aprikot.data.repository.QuizQuestionRepositoryImpl
import org.aprikot.data.repository.QuizTopicRepositoryImpl
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.repository.QuizTopicRepository
import org.aprikot.presentation.routes.quiz_question.deleteQuizQuestionById
import org.aprikot.presentation.routes.quiz_question.getAllQuizQuestions
import org.aprikot.presentation.routes.quiz_question.getQuizQuestionById
import org.aprikot.presentation.routes.quiz_question.upsertQuizQuestions
import org.aprikot.presentation.routes.quiz_question.upsertMultipleQuestions
import org.aprikot.presentation.routes.quiz_topic.deleteQuizTopicById
import org.aprikot.presentation.routes.quiz_topic.getAllQuizTopics
import org.aprikot.presentation.routes.quiz_topic.getQuizTopicById
import org.aprikot.presentation.routes.quiz_topic.upsertMultipleTopics
import org.aprikot.presentation.routes.quiz_topic.upsertQuizTopic
import org.aprikot.presentation.routes.root

fun Application.configureRouting() {

    install(Resources)

    val mongoDatabase = DatabaseFactory.create()
    val quizQuestionRepository: QuizQuestionRepository = QuizQuestionRepositoryImpl(mongoDatabase)
    val quizTopicRepository: QuizTopicRepository = QuizTopicRepositoryImpl(mongoDatabase)

    routing {

        root()

        //Quiz Questions
        getAllQuizQuestions(quizQuestionRepository)
        upsertQuizQuestions(quizQuestionRepository)
        upsertMultipleQuestions(quizQuestionRepository)
        deleteQuizQuestionById(quizQuestionRepository)
        getQuizQuestionById(quizQuestionRepository)

        //Quiz Topics
        getAllQuizTopics(quizTopicRepository)
        upsertQuizTopic(quizTopicRepository)
        upsertMultipleTopics(quizTopicRepository)
        deleteQuizTopicById(quizTopicRepository)
        getQuizTopicById(quizTopicRepository)

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}