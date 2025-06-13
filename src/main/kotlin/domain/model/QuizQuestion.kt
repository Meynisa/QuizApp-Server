package org.aprikot.domain.model

import kotlinx.serialization.Serializable
import org.aprikot.data.utils.DateSerializer
import java.util.Date

@Serializable
data class QuizQuestion(
    val id: String? = null,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val explanation : String,
    val topicCode: Int,
    @Serializable(with = DateSerializer::class)
    val createdAt: Date? = null,
    @Serializable(with = DateSerializer::class)
    val updatedAt: Date? = null
)
