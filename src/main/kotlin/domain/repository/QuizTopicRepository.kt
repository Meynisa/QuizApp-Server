package org.aprikot.domain.repository

import org.aprikot.domain.model.QuizTopic
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result

interface QuizTopicRepository {

    suspend fun getAllTopics(): Result<List<QuizTopic>, DataError>

    suspend fun upsertTopic(topic: QuizTopic): Result<Unit, DataError>

    suspend fun upsertMultipleTopic(listOfTopic: List<QuizTopic>): Result<Unit, DataError>

    suspend fun getTopicById(id: String?): Result<QuizTopic, DataError>

    suspend fun deleteTopicById(id: String?): Result<Unit, DataError>
}