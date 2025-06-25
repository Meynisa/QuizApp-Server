package org.aprikot.di

import data.repository.UserRepositoryImpl
import domain.repository.UserRepository
import org.aprikot.data.database.DatabaseFactory
import org.aprikot.data.repository.IssueReportRepositoryImpl
import org.aprikot.data.repository.QuizQuestionRepositoryImpl
import org.aprikot.data.repository.QuizTopicRepositoryImpl
import org.aprikot.domain.repository.IssueReportRepository
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.repository.QuizTopicRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import security.hashing.HashingRepository
import security.hashing.HashingRepositoryImpl
import security.token.TokenRepository
import security.token.TokenRepositoryImpl

val koinModule = module {

    single { DatabaseFactory.create() }
    singleOf(::HashingRepositoryImpl).bind<HashingRepository>()
    singleOf(::TokenRepositoryImpl).bind<TokenRepository>()
    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportRepositoryImpl).bind<IssueReportRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}