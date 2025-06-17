package org.aprikot.domain.model

import kotlinx.serialization.Serializable
import org.aprikot.data.utils.DateSerializer
import java.util.Date

@Serializable
data class QuizTopic(
  val id: String? = null,
  val name: String,
  val imageUrl: String,
  val code: Int,
  @Serializable(with = DateSerializer::class)
  val createdAt: Date? = null,
  @Serializable(with = DateSerializer::class)
  val updatedAt: Date? = null
)
