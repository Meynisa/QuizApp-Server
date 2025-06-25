package security.token

interface TokenRepository {

    fun generate(
        config: TokenConfig,
        vararg claims: TokenClaim
    ): String
}