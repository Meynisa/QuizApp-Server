package presentation.routes.user

import domain.repository.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.getUserByUsername(
    userRepository: UserRepository
) {
    get<UserRoutesPath.ByUsername> { path ->
        userRepository.getUserByUsername(path.username)
            .onSuccess { userData ->
                call.respond(
                    message = userData,
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}