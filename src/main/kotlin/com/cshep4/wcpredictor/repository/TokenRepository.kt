package com.cshep4.wcpredictor.repository

import com.cshep4.wcpredictor.constant.Queries.QUERY_IS_TOKEN_USED
import com.cshep4.wcpredictor.constant.Queries.QUERY_SET_TOKEN_TO_USED
import com.cshep4.wcpredictor.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<TokenEntity, String> {
    @Query(value = QUERY_IS_TOKEN_USED, nativeQuery = true)
    fun isTokenUsed(token: String): Boolean?

    @Modifying(clearAutomatically = true)
    @Query(value = QUERY_SET_TOKEN_TO_USED, nativeQuery = true)
    fun setTokenToUsed(token: String): Int
}