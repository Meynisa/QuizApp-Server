package org.aprikot.data.database.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.Date

data class IssueReportEntity(
    @BsonId
    val _id: String = ObjectId().toString(),
    val questionId: String,
    val issueType:String,
    val additionalComment: String?,
    val userEmail: String?,
    val timestamp: String,
    val createdAt: Date?,
)
