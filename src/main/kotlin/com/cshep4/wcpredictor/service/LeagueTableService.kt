package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.leaguetable.GroupCreator
import com.cshep4.wcpredictor.component.leaguetable.LeagueTableCalculator
import com.cshep4.wcpredictor.data.LeagueTable
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.Standings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LeagueTableService {
    @Autowired
    private lateinit var fixturesService: FixturesService

    @Autowired
    private lateinit var groupCreator: GroupCreator

    @Autowired
    private lateinit var leagueTableCalculator: LeagueTableCalculator

    fun getCurrentStandings() : Standings {
        val matches = fixturesService.retrieveAllMatches()

        val currentStandings = createStandingsFromMatches(matches)

        return Standings(currentStandings)
    }

    fun getPredictedStandings(id: Long): Standings {
        val matches = fixturesService.retrieveAllPredictedMatchesByUserId(id)

        val currentStandings = createStandingsFromMatches(matches)

        return Standings(currentStandings)
    }

    private fun createStandingsFromMatches(matches: List<Match>) : List<LeagueTable> {
        val groups = groupCreator.create()

        return leagueTableCalculator.calculate(matches, groups)
    }
}