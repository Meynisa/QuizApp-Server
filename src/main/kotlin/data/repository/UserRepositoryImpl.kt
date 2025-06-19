package data.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import data.database.entity.UserEntity
import data.mapper.toUser
import data.mapper.toUserEntity
import domain.model.User
import domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import org.aprikot.data.utils.Constant.USERS_COLLECTION_NAME
import org.aprikot.domain.util.DataError
import org.aprikot.domain.util.Result

class UserRepositoryImpl (
    mongoDatabase: MongoDatabase
): UserRepository {

    private val userCollection = mongoDatabase
        .getCollection<UserEntity>(USERS_COLLECTION_NAME)

    override suspend fun getUserByUsername(username: String): Result<User, DataError> {
        if (username.isEmpty()){
            return Result.Failure(DataError.Validation)
        }

        return try {
            val filterQuery = Filters.eq(
                UserEntity::username.name,  username
            )
            val userEntity = userCollection
                .find(filterQuery)
                .firstOrNull()

            if (userEntity != null) {
                val user = userEntity.toUser()
                Result.Success(user)
            } else {
                Result.Failure(DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }


    }

    override suspend fun insertUser(user: User): Result<Unit, DataError> {
        return try {
            userCollection.insertOne(user.toUserEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

}