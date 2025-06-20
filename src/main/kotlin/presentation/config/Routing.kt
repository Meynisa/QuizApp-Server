package org.aprikot.presentation.config

import domain.repository.UserRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import kotlinx.coroutines.DelicateCoroutinesApi
import org.aprikot.domain.repository.IssueReportRepository
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.repository.QuizTopicRepository
import org.aprikot.presentation.routes.issue_report.deleteIssueReportById
import org.aprikot.presentation.routes.issue_report.getAllIssueReports
import org.aprikot.presentation.routes.issue_report.insertIssueReport
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
import org.koin.ktor.ext.inject
import presentation.routes.user.authenticateRoute
import presentation.routes.user.getSecretInfoRoute
import presentation.routes.user.loginRoute
import presentation.routes.user.registerRoute
import security.hashing.HashingRepository
import security.token.TokenConfig
import security.token.TokenRepository

@OptIn(DelicateCoroutinesApi::class)
fun Application.configureRouting(
    tokenConfig: TokenConfig
) {

    install(Resources)

    val quizQuestionRepository: QuizQuestionRepository by inject()
    val quizTopicRepository: QuizTopicRepository by inject()
    val issueReportRepository: IssueReportRepository by inject()
    val userRepository: UserRepository by inject()
    val hashingRepository: HashingRepository by inject()
    val tokenRepository: TokenRepository by inject()

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

        //Issue Report
        getAllIssueReports(issueReportRepository)
        insertIssueReport(issueReportRepository)
        deleteIssueReportById(issueReportRepository)

        //Auth
        loginRoute(userRepository, hashingRepository, tokenRepository, tokenConfig)
        registerRoute(hashingRepository, userRepository)
        authenticateRoute()
        getSecretInfoRoute()

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}