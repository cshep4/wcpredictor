package com.cshep4.wcpredictor.repository

import com.cshep4.wcpredictor.constant.Queries.QUERY_GET_LEAGUE_DETAILS
import com.cshep4.wcpredictor.constant.Queries.QUERY_GET_OVERALL_LEAGUE_DETAILS
import com.cshep4.wcpredictor.entity.LeagueTableUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LeagueTableRepository : JpaRepository<LeagueTableUserEntity, Long> {
    @Query(value = QUERY_GET_LEAGUE_DETAILS, nativeQuery = true)
    fun getLeagueTable(pin: Long): List<LeagueTableUserEntity>

    @Query(value = QUERY_GET_OVERALL_LEAGUE_DETAILS, nativeQuery = true)
    fun getOverallLeagueTable(): List<LeagueTableUserEntity>
}