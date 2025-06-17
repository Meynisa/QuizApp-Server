package org.aprikot.data.database.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.Date

data class  QuizQuestionEntity(
    @BsonId
    val _id: String = ObjectId().toString(),
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val explanation : String,
    val topicCode: Int,
    val createdAt: Date?,
    val updatedAt: Date?
)
