package org.aprikot.data.repository

import org.aprikot.domain.model.QuizQuestion
import org.aprikot.domain.repository.QuizQuestionRepository

class QuizQuestionRepositoryImpl : QuizQuestionRepository {

    private val quizQuestions = mutableListOf<QuizQuestion>()

    override fun upsertQuestion(question: QuizQuestion) {
        quizQuestions.add(question)
    }

    override fun getAllQuestions(
        topicCode: Int?,
        limit: Int?
    ): List<QuizQuestion> {
        return if (topicCode != null){
            quizQuestions
                .filter { it.topicCode == topicCode }
                .take(limit ?: quizQuestions.size)
        }else{
            quizQuestions.take(limit ?: quizQuestions.size)
        }

    }

    override fun getQuestionById(id: String): QuizQuestion? {
        return quizQuestions.find { it.id == id }
    }

    override fun deleteQuestionById(id: String): Boolean {
        return quizQuestions.removeIf { it.id == id }
    }

}