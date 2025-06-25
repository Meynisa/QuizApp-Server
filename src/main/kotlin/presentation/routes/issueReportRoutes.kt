package presentation.routes

import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.aprikot.domain.model.IssueReport
import org.aprikot.domain.repository.IssueReportRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import presentation.routes.path.IssueReportRoutesPath
import org.aprikot.presentation.util.respondWithError

fun Route.issueReportRoutes(
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

    authenticate {
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

    authenticate {
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
}