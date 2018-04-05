package com.cshep4.wcpredictor.repository

import com.cshep4.wcpredictor.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<TokenEntity, String> {
    @Query(value = "SELECT COUNT(*) FROM Token WHERE token = ?1", nativeQuery = true)
    fun getNumberOfTokens(token: String): Int
}