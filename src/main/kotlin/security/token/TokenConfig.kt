package security.token

import io.ktor.server.application.ApplicationEnvironment

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expiresIn: Long,
    val secret: String
)

object TokenUtils {
    fun createConfig(environment: ApplicationEnvironment): TokenConfig {
        return TokenConfig(
            issuer = environment.config.property("jwt.issuer").getString(),
            audience = environment.config.property("jwt.audience").getString(),
            expiresIn = 365L * 1000L * 60L * 60L * 24L,
            secret = System.getenv("JWT_SECRET")
        )
    }
}