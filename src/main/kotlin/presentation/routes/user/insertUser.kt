package presentation.routes.user

import domain.model.User
import domain.repository.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.insertUser(
    userRepository: UserRepository
) {
    post<UserRoutesPath> {
        val userData = call.receive<User>()

        userRepository.insertUser(userData)
            .onSuccess {
                call.respond(
                    message = "User submitted successfully",
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}