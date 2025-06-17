package org.aprikot.domain.repository

import org.aprikot.domain.model.IssueReport
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result

interface IssueReportRepository {

    suspend fun getAllIssueReport(): Result<List<IssueReport>, DataError>

    suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError>

    suspend fun deleteIssueReportById(id: String?): Result<Unit, DataError>
}