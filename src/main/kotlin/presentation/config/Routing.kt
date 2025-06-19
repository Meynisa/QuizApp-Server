package org.aprikot.presentation.config

import domain.model.User
import domain.repository.UserRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
import presentation.routes.user.getUserByUsername
import presentation.routes.user.insertUser
import java.util.Date

@OptIn(DelicateCoroutinesApi::class)
fun Application.configureRouting() {

    install(Resources)

    val quizQuestionRepository: QuizQuestionRepository by inject()
    val quizTopicRepository: QuizTopicRepository by inject()
    val issueReportRepository: IssueReportRepository by inject()
    val userRepository: UserRepository by inject()

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

        //User
        getUserByUsername(userRepository)
        insertUser(userRepository)

//        GlobalScope.launch {
//            val user = User(
//                username = "test",
//                password = "test-password",
//                salt = "salt",
//                createdAt = Date()
//            )
//            userRepository.insertUser(user)
//        }

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}