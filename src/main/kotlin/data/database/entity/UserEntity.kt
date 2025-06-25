package data.database.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.Date

data class UserEntity(
    @BsonId
    val _id: String = ObjectId().toString(),
    val username: String,
    val password: String,
    val salt: String,
    val createdAt: Date?
)
