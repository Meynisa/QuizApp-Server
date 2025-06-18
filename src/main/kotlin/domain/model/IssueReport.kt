package org.aprikot.domain.model

import kotlinx.serialization.Serializable
import org.aprikot.data.utils.DateSerializer
import java.util.Date

@Serializable
data class IssueReport(
    val id: String? = null,
    val questionId: String,
    val issueType:String,
    val additionalComment: String?,
    val userEmail: String?,
    val timestamp: String,
    @Serializable(with = DateSerializer::class)
    val createdAt: Date? = null,
)