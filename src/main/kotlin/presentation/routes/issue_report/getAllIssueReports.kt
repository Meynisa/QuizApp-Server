package org.aprikot.presentation.routes.issue_report

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.aprikot.domain.repository.IssueReportRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.getAllIssueReports(
    issueReportRepository: IssueReportRepository
) {
    get<IssueReportRoutesPath> {
        issueReportRepository.getAllIssueReport()
            .onSuccess { reports ->
                call.respond(
                    message = reports,
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}