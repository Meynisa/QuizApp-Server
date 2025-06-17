package org.aprikot.data.mapper

import org.aprikot.data.database.entity.IssueReportEntity
import org.aprikot.data.database.entity.QuizTopicEntity
import org.aprikot.domain.model.IssueReport
import org.aprikot.domain.model.QuizTopic
import java.util.Date

fun IssueReportEntity.toIssueReport() = IssueReport(
    id = _id,
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timestamp
)

fun IssueReport.toIssueReportEntity() = IssueReportEntity(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timestamp
)