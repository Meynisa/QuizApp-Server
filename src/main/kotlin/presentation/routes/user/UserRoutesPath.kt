package presentation.routes.user

import io.ktor.resources.Resource

@Resource(path = "user")
class UserRoutesPath {

    @Resource(path = "/{username}")
    data class ByUsername(
        val parent: UserRoutesPath = UserRoutesPath(),
        val username: String
    )
}