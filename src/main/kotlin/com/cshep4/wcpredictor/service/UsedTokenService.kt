package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.entity.TokenEntity
import com.cshep4.wcpredictor.repository.TokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UsedTokenService {
    @Autowired
    private lateinit var tokenRepository: TokenRepository

    fun hasTokenBeenUsed(token: String): Boolean {
        return tokenRepository.isTokenUsed(token) ?: return false
    }

    fun addUsedToken(token: String): String {
        val tokenEntity = TokenEntity(token)

        val insertedToken = tokenRepository.save(tokenEntity)
        return insertedToken.token
    }

    fun setUsedToken(token: String): Int {
        return tokenRepository.setTokenToUsed(token)
    }
}