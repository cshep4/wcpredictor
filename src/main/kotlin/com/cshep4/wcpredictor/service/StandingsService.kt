package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.data.OverallLeagueOverview
import com.cshep4.wcpredictor.data.StandingsOverview
import com.cshep4.wcpredictor.data.UserLeagueOverview
import com.cshep4.wcpredictor.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class StandingsService {
    companion object {
        const val LEAGUE_NUMBER_INDEX = 0
        const val PIN_INDEX = 1
        const val LEAGUE_RANK_INDEX = 2
        const val COUNT_INDEX = 1
        const val OVERALL_RANK_INDEX = 3
    }

    @Autowired
    lateinit var userRepository: UserRepository

    fun retrieveStandingsOverview(id: Long) : StandingsOverview {
        val userLeagues = getUsersLeagueList(id)

        val overallLeagueOverview = getOverallLeagueOverview(id)

        return StandingsOverview(overallLeagueOverview, userLeagues)
    }

    private fun getUsersLeagueList(id: Long) : List<UserLeagueOverview> {
        val rawData = userRepository.getUsersLeagueList(id)

        return rawData.map { UserLeagueOverview(it[LEAGUE_NUMBER_INDEX] as String, (it[PIN_INDEX] as BigInteger).toLong(), (it[LEAGUE_RANK_INDEX] as BigInteger).toLong()) }
    }

    private fun getOverallLeagueOverview(id: Long) : OverallLeagueOverview {
        val rawData = userRepository.getOverallLeagueOverview(id)[0]

        return OverallLeagueOverview((rawData[OVERALL_RANK_INDEX] as BigInteger).toLong(), (rawData[COUNT_INDEX] as BigInteger).toLong())
    }
}