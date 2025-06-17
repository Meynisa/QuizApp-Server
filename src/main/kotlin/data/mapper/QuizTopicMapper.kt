package org.aprikot.data.mapper

import org.aprikot.data.database.entity.QuizTopicEntity
import org.aprikot.domain.model.QuizTopic
import java.util.Date

fun QuizTopicEntity.toQuizTopic() = QuizTopic(
    id = _id,
    name = name,
    imageUrl = imageUrl,
    code = code,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun QuizTopic.toQuizTopicEntity() = QuizTopicEntity(
    name = name,
    imageUrl = imageUrl,
    code = code,
    createdAt = Date(),
    updatedAt = updatedAt
)