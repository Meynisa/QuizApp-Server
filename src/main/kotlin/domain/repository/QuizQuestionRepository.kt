package org.aprikot.domain.repository

import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result

interface QuizQuestionRepository {

    suspend fun getAllQuestions(topicCode: Int?, limit: Int?): Result<List<QuizQuestion>, DataError>

    suspend fun upsertQuestion(question: QuizQuestion): Result<Unit, DataError>

    suspend fun getQuestionById(id: String?): Result<QuizQuestion, DataError>

    suspend fun deleteQuestionById(id: String?): Result<Unit, DataError>

    suspend fun upsertMultipleQuestions(listOfQuestions: List<QuizQuestion>): Result<Unit, DataError>

}