package org.aprikot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizTopic(
  val id: String? = null,
    val name: String,
    val imageUrl: String,
    val code: Int
)
