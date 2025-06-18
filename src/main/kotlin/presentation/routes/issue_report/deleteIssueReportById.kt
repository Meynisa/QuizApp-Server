package org.aprikot.presentation.routes.issue_report

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.aprikot.domain.repository.IssueReportRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.deleteIssueReportById (
    issueReportRepository: IssueReportRepository
) {
    delete<IssueReportRoutesPath.ById> { path ->
        issueReportRepository.deleteIssueReportById(path.reportId)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}