package org.aprikot.data.mapper

import org.aprikot.data.database.entity.QuizQuestionEntity
import org.aprikot.domain.model.QuizQuestion

fun QuizQuestionEntity.toQuizQuestion() = QuizQuestion (
    id = _id,
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation,
    topicCode = topicCode
)

fun QuizQuestion.toQuizQuestionEntity() = QuizQuestionEntity (
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation,
    topicCode = topicCode
)