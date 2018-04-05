package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.entity.TokenEntity
import com.cshep4.wcpredictor.repository.TokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsedTokenService {
    @Autowired
    private lateinit var tokenRepository: TokenRepository

    fun hasTokenBeenUsed(token: String): Boolean {
        val numberOfUses = tokenRepository.getNumberOfTokens(token)

        return numberOfUses > 0
    }

    fun addUsedToken(token: String): String {
        val tokenEntity = TokenEntity(token)

        val insertedToken = tokenRepository.save(tokenEntity)
        return insertedToken.token
    }
}