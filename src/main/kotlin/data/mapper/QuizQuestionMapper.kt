package org.aprikot.data.mapper

import org.aprikot.data.database.entity.QuizQuestionEntity
import org.aprikot.domain.model.QuizQuestion
import java.util.Date

fun QuizQuestionEntity.toQuizQuestion() = QuizQuestion (
    id = _id,
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation,
    topicCode = topicCode,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun QuizQuestion.toQuizQuestionEntity() = QuizQuestionEntity (
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation,
    topicCode = topicCode,
    createdAt = Date(),
    updatedAt = updatedAt
)