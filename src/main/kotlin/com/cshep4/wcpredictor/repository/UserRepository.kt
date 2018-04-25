package com.cshep4.wcpredictor.repository

import com.cshep4.wcpredictor.constant.Queries.QUERY_GET_OVERALL_LEAGUE_OVERVIEW
import com.cshep4.wcpredictor.constant.Queries.QUERY_GET_SCORE_AND_RANK
import com.cshep4.wcpredictor.constant.Queries.QUERY_GET_USERS_LEAGUE_LIST
import com.cshep4.wcpredictor.constant.Queries.QUERY_GET_USER_BY_EMAIL
import com.cshep4.wcpredictor.constant.Queries.QUERY_SAVE_USER
import com.cshep4.wcpredictor.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    @Query(value = QUERY_GET_USER_BY_EMAIL, nativeQuery = true)
    fun findByEmail(email: String): Optional<UserEntity>

    @Query(value = QUERY_SAVE_USER, nativeQuery = true)
    fun save(email: String, password: String): Optional<UserEntity>?

    @Query(value = QUERY_GET_SCORE_AND_RANK, nativeQuery = true)
    fun getUserRankAndScore(): List<Array<Any>>

    @Query(value = QUERY_GET_USERS_LEAGUE_LIST, nativeQuery = true)
    fun getUsersLeagueList(id: Long): List<Array<Any>>

    @Query(value = QUERY_GET_OVERALL_LEAGUE_OVERVIEW, nativeQuery = true)
    fun getOverallLeagueOverview(id: Long): List<Array<Any>>
}