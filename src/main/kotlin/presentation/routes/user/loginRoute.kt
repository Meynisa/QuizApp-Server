package presentation.routes.user

import domain.model.AuthRequest
import domain.model.AuthResponse
import domain.repository.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.apache.commons.codec.digest.DigestUtils
import org.aprikot.data.utils.Constant.CLAIM_NAME
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError
import security.hashing.HashingRepository
import security.hashing.SaltedHash
import security.token.TokenClaim
import security.token.TokenConfig
import security.token.TokenRepository

fun Route.loginRoute(
    userRepository: UserRepository,
    hashingRepository: HashingRepository,
    tokenRepository: TokenRepository,
    tokenConfig: TokenConfig
) {
    post("login") {
        val request = runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

//        val userData = userRepository.getUserByUsername(request.username)
        userRepository.getUserByUsername(request.username)
            .onSuccess { userData ->
               val isValidPassword = hashingRepository.verify(
                   value = request.password,
                   saltedHash = SaltedHash(
                       hash = userData.password,
                       salt = userData.salt
                   )
               )

                if (!isValidPassword) {
                    println("Entered hash: ${DigestUtils.sha256Hex("${userData.salt}${request.password}")}, Hashed PW: ${userData.password}")
                    call.respond(
                        message = "Incorrect username or password2",
                        status = HttpStatusCode.Conflict
                    )
                    return@post
                }

                val token = tokenRepository.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = CLAIM_NAME,
                        value = userData.id.toString()
                    )
                )

                call.respond(
                    message = AuthResponse(token = token),
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}