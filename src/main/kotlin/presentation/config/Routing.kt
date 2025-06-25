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
import org.aprikot.presentation.routes.root
import org.koin.ktor.ext.inject
import presentation.routes.authRoutes
import presentation.routes.issueReportRoutes
import presentation.routes.quizQuestionRoutes
import presentation.routes.quizTopicRoutes
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
        quizQuestionRoutes(quizQuestionRepository)
        quizTopicRoutes(quizTopicRepository)
        issueReportRoutes(issueReportRepository)
        authRoutes(userRepository, hashingRepository, tokenRepository, tokenConfig)

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}