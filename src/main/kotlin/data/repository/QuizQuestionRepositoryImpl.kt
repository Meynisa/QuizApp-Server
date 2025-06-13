package org.aprikot.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
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
import java.util.Date

class QuizQuestionRepositoryImpl(
    mongoDatabase: MongoDatabase
) : QuizQuestionRepository {

    val questionCollection = mongoDatabase
        .getCollection<QuizQuestionEntity>(QUESTIONS_COLLECTION_NAME)

    override suspend fun upsertQuestion(question: QuizQuestion) {
        try{
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
                questionCollection.updateOne(filterQuery, updateQuery)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun upsertMultipleQuestions(listOfQuestions: List<QuizQuestion>){
        try{
            val listQuestionsEntity: List<QuizQuestionEntity> = listOfQuestions
                .map { it.toQuizQuestionEntity() }
                .toList()
            questionCollection.insertMany(
                listQuestionsEntity
            )

        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun getAllQuestions(
        topicCode: Int?,
        limit: Int?
    ): List<QuizQuestion> {
       return try {
           val questionLimit = limit?.takeIf { it > 0 } ?: 20
           val filterQuery = topicCode?.let {
               Filters.eq(QuizQuestionEntity::topicCode.name, it)
           } ?: Filters.empty()

           questionCollection
               .find(filter = filterQuery)
               .limit(questionLimit)
               .map { it.toQuizQuestion() }
               .toList()

       } catch (e: Exception){
           e.printStackTrace()
           emptyList()
       }
    }

    override suspend fun getQuestionById(id: String): QuizQuestion? {
        return try{
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, id
            )
            val questionEntity = questionCollection
                .find(filterQuery)
                .firstOrNull()
            questionEntity?.toQuizQuestion()
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun deleteQuestionById(id: String): Boolean {
        return try {
            val filterQuery = Filters.eq(
                QuizQuestionEntity::_id.name, id
            )
            val deleteResult = questionCollection
                .deleteOne(filter = filterQuery)
            deleteResult.deletedCount > 0
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

}