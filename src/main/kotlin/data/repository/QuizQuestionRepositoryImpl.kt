package org.aprikot.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.client.result.InsertManyResult
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.aprikot.data.database.entity.QuizQuestionEntity
import org.aprikot.data.mapper.toQuizQuestion
import org.aprikot.data.mapper.toQuizQuestionEntity
import org.aprikot.data.utils.Constant.QUESTIONS_COLLECTION_NAME
import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result
import java.util.Date

class QuizQuestionRepositoryImpl(
    mongoDatabase: MongoDatabase
) : QuizQuestionRepository {

    val questionCollection = mongoDatabase
        .getCollection<QuizQuestionEntity>(QUESTIONS_COLLECTION_NAME)

    override suspend fun upsertQuestion(question: QuizQuestion): Result<Unit, DataError> {
        return try{
            if (question.id == null){
                questionCollection.insertOne(question.toQuizQuestionEntity())
            } else {
                val filterQuery = Filters.eq(
                    QuizQuestionEntity::_id.name, question.id
                )

                val updateQuery = Updates.combine(
                    Updates.set(QuizQuestionEntity::question.name, question.question),
                    Updates.set(QuizQuestionEntity::correctAnswer.name, question.correctAnswer),
                    Updates.set(QuizQuestionEntity::incorrectAnswers.name, question.incorrectAnswers),
                    Updates.set(QuizQuestionEntity::explanation.name, question.explanation),
                    Updates.set(QuizQuestionEntity::topicCode.name, question.topicCode),
                    Updates.set(QuizQuestionEntity::updatedAt.name, Date()),
                )

                val updateResult = questionCollection.updateOne(filterQuery, updateQuery)

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

    override suspend fun upsertMultipleQuestions(listOfQuestions: List<QuizQuestion>): Result<Unit, DataError>{
        return try{
            val listQuestionsEntity: List<QuizQuestionEntity> = listOfQuestions
                .map { it.toQuizQuestionEntity() }
                .toList()
            val updateResult = questionCollection.insertMany(
                listQuestionsEntity
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

    override suspend fun getAllQuestions(
        topicCode: Int?,
        limit: Int?
    ): Result<List<QuizQuestion>, DataError> {
       return try {
           val questionLimit = limit?.takeIf { it > 0 } ?: 20
           val filterQuery = topicCode?.let {
               Filters.eq(QuizQuestionEntity::topicCode.name, it)
           } ?: Filters.empty()

           val questions = questionCollection
               .find(filter = filterQuery)
               .limit(questionLimit)
               .map { it.toQuizQuestion() }
               .toList()

           if (questions.isNotEmpty()){
               Result.Success(questions)
           } else {
               Result.Failure(DataError.NotFound)
           }
       } catch (e: Exception){
           e.printStackTrace()
           Result.Failure(DataError.Database)
       }
    }

    override suspend fun getQuestionById(id: String?): Result<QuizQuestion, DataError> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataError.Validation)
        }

        return try{
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, id
            )
            val questionEntity = questionCollection
                .find(filterQuery)
                .firstOrNull()
            if (questionEntity != null){
                val question = questionEntity.toQuizQuestion()
                Result.Success(question)
            } else {
                Result.Failure(DataError.NotFound)
            }

        } catch (e: Exception){
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun deleteQuestionById(id: String?): Result<Unit, DataError> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataError.Validation)
        }

        return try {
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, id
            )

            val deleteResult = questionCollection
                .deleteOne(filter = filterQuery)

            if (deleteResult.deletedCount > 0){
                Result.Success(Unit)
            } else {
                Result.Failure(DataError.NotFound)
            }
        } catch (e: Exception){
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

}