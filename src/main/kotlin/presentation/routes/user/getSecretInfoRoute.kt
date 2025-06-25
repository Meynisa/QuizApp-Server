package presentation.routes.user

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.aprikot.data.utils.Constant.CLAIM_NAME
import io.ktor.server.response.respond

fun Route.getSecretInfoRoute() {
    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim(CLAIM_NAME, String::class)

            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}