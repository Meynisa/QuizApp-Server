package presentation.routes

import domain.model.AuthRequest
import domain.model.AuthResponse
import domain.model.User
import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import domain.repository.UserRepository
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.get
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

fun Route.authRoutes(
    userRepository: UserRepository,
    hashingRepository: HashingRepository,
    tokenRepository: TokenRepository,
    tokenConfig: TokenConfig
) {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }

    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim(CLAIM_NAME, String::class)

            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }

    post("login") {
        val request = runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

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

    post(path = "register") {
        val request = runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordTooShort = request.password.length < 8

        if (areFieldsBlank || isPasswordTooShort) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val saltedHash = hashingRepository.generateSaltedHash(request.password)
        val user = User(
            username = request.username,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )

        userRepository.insertUser(user)
            .onSuccess {
                call.respond(
                    message = "Registration is successful",
                    status = HttpStatusCode.OK
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}