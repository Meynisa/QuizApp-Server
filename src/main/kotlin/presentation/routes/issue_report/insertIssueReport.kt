package org.aprikot.presentation.routes.issue_report

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.*
import io.ktor.server.routing.Route
import org.aprikot.domain.model.IssueReport
import org.aprikot.domain.repository.IssueReportRepository
import org.aprikot.domain.util.onSuccess
import io.ktor.server.response.respond
import org.aprikot.domain.util.onFailure
import org.aprikot.presentation.util.respondWithError

fun Route.insertIssueReport(
    issueReportRepository: IssueReportRepository
) {
    post<IssueReportRoutesPath> {
        val issue = call.receive<IssueReport>()

        issueReportRepository.insertIssueReport(issue)
            .onSuccess {
                call.respond(
                    message = "Report submitted successfully",
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}