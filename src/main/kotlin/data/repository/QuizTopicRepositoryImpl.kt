package org.aprikot.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.aprikot.data.database.entity.QuizTopicEntity
import org.aprikot.data.mapper.toQuizTopic
import org.aprikot.data.mapper.toQuizTopicEntity
import org.aprikot.data.utils.Constant.TOPICS_COLLECTION_NAME
import org.aprikot.domain.model.QuizTopic
import org.aprikot.domain.repository.QuizTopicRepository
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result
import java.util.Date
import kotlin.collections.map
import kotlin.collections.toList

class QuizTopicRepositoryImpl(
    mongoDatabase: MongoDatabase
) : QuizTopicRepository{
    val topicCollection = mongoDatabase
        .getCollection<QuizTopicEntity>(TOPICS_COLLECTION_NAME)

    override suspend fun getAllTopics(): Result<List<QuizTopic>, DataError> {
        return try {
            val sortQuery = Sorts.ascending(
                QuizTopicEntity::code.name
            )

            val topics = topicCollection
                .find()
                .sort(sortQuery)
                .map { it.toQuizTopic() }
                .toList()

            if (topics.isNotEmpty()) {
                Result.Success(topics)
            } else {
                Result.Failure(DataError.NotFound)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun upsertTopic(topic: QuizTopic): Result<Unit, DataError> {
        return try {
            if (topic.id == null) {
                topicCollection.insertOne(topic.toQuizTopicEntity())
            } else {

                val filterQuery = Filters.eq(
                    QuizTopicEntity::_id.name, topic.id
                )

                val updateQuery = Updates.combine(
                    Updates.set(QuizTopicEntity::name.name, topic.name),
                    Updates.set(QuizTopicEntity::imageUrl.name, topic.imageUrl),
                    Updates.set(QuizTopicEntity::code.name, topic.code),
                    Updates.set(QuizTopicEntity::updatedAt.name, Date())
                )

                val updateResult = topicCollection.updateOne(filterQuery, updateQuery)

                if (updateResult.modifiedCount == 0L) {
                    return Result.Failure(DataError.NotFound)
                }
            }
            Result.Success(Unit)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun upsertMultipleTopic(listOfTopic: List<QuizTopic>): Result<Unit, DataError> {
        return try{
            val listTopicEntity: List<QuizTopicEntity> = listOfTopic
                .map { it.toQuizTopicEntity() }
                .toList()
            val updateResult = topicCollection.insertMany(
                listTopicEntity
            )

            if (!updateResult.wasAcknowledged()) {
                return Result.Failure(DataError.NotFound)
            }

            Result.Success(Unit)

        } catch (e: Exception){
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun getTopicById(id: String?): Result<QuizTopic, DataError> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataError.Validation)
        }
        return try {
            val filterQuery = Filters.eq(
                QuizTopicEntity::_id.name, id
            )

            val topicEntity = topicCollection
                .find(filterQuery)
                .firstOrNull()

            if (topicEntity != null) {
                val topic = topicEntity.toQuizTopic()
                Result.Success(topic)
            } else {
                Result.Failure(DataError.NotFound)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun deleteTopicById(id: String?): Result<Unit, DataError> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataError.Validation)
        }
        return try {
            val filterQuery = Filters.eq(
                QuizTopicEntity::_id.name, id
            )

            val deleteResult = topicCollection
                .deleteOne(filter = filterQuery)

            if (deleteResult.deletedCount > 0){
                Result.Success(Unit)
            } else {
                Result.Failure(DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }
}