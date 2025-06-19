package domain.repository

import domain.model.User
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result

interface UserRepository {

    suspend fun getUserByUsername(username: String): Result<User, DataError>

    suspend fun insertUser(user: User): Result<Unit, DataError>

}