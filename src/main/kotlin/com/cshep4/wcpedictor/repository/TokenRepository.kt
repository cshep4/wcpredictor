package com.cshep4.wcpedictor.repository

import com.cshep4.wcpedictor.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<TokenEntity, String> {
    @Query(value = "SELECT COUNT(*) FROM Token WHERE token = ?1", nativeQuery = true)
    fun getNumberOfTokens(token: String): Int
}