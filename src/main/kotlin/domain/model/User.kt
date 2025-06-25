package domain.model

import kotlinx.serialization.Serializable
import org.aprikot.data.utils.DateSerializer
import java.util.Date

@Serializable
data class User(
    val id: String? = null,
    val username: String,
    val password: String,
    val salt: String,
    @Serializable(with = DateSerializer::class)
    val createdAt: Date? = null
)
