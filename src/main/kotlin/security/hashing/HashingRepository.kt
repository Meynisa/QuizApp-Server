package security.hashing

interface HashingRepository {

    fun generateSaltedHash(value: String, saltLength: Int = 10): SaltedHash

    fun verify(value: String, saltedHash: SaltedHash): Boolean
}