package data.mapper

import data.database.entity.UserEntity
import domain.model.User
import java.util.Date

fun UserEntity.toUser() = User(
    id = _id,
    username = username,
    password = password,
    salt = salt,
    createdAt = createdAt
)

fun User.toUserEntity() = UserEntity(
    username = username,
    password = password,
    salt = salt,
    createdAt = Date(),
)