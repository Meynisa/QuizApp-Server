package org.aprikot.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.aprikot.data.database.entity.IssueReportEntity
import org.aprikot.data.mapper.toIssueReport
import org.aprikot.data.mapper.toIssueReportEntity
import org.aprikot.data.utils.Constant.ISSUE_REPORTS_COLLECTION_NAME
import org.aprikot.domain.model.IssueReport
import org.aprikot.domain.repository.IssueReportRepository
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result

class IssueReportRepositoryImpl (
    mongoDatabase: MongoDatabase
): IssueReportRepository {

    private val reportCollection = mongoDatabase
        .getCollection<IssueReportEntity>(ISSUE_REPORTS_COLLECTION_NAME)

    override suspend fun getAllIssueReport(): Result<List<IssueReport>, DataError> {
        return try {
            val reports= reportCollection
                .find()
                .map { it.toIssueReport() }
                .toList()

            if (reports.isNotEmpty()) {
                Result.Success(reports)
            } else {
                Result.Failure(DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError> {
        return try {
            reportCollection.insertOne(report.toIssueReportEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun deleteIssueReportById(id: String?): Result<Unit, DataError> {
        if (id.isNullOrBlank()){
            return Result.Failure(DataError.Validation)
        }

        return try {
            val filterQuery = Filters.eq(
                IssueReportEntity::_id.name, id
            )

            val deleteResult = reportCollection.deleteOne(filterQuery)

            if (deleteResult.deletedCount > 0) {
                Result.Success(Unit)
            } else {
                Result.Failure(DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }
}