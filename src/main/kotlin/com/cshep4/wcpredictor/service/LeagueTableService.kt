package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.fixtures.KnockoutFixturesCollector
import com.cshep4.wcpredictor.component.leaguetable.GroupCreator
import com.cshep4.wcpredictor.component.leaguetable.LeagueTableCalculator
import com.cshep4.wcpredictor.constant.RoundConstants.FINAL_GROUP_MATCHDAY
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

    @Autowired
    private lateinit var knockoutFixturesCollector: KnockoutFixturesCollector

    fun getCurrentStandings() : Standings {
        val matches = fixturesService.retrieveAllMatches()

        return getStandingsFromMatches(matches)
    }

    fun getPredictedStandings(id: Long): Standings {
        val matches = fixturesService.retrieveAllMatchesWithPredictions(id)
                .map { it.toMatch() }

        return getStandingsFromMatches(matches)
    }

    private fun getStandingsFromMatches(matches: List<Match>): Standings {
        val groupMatches = matches.filter { it.matchday <= FINAL_GROUP_MATCHDAY }
        val knockoutMatches = matches.filter { it.matchday > FINAL_GROUP_MATCHDAY }

        val currentStandings = createGroupStandingsFromMatches(groupMatches)

        val knockoutStandings = knockoutFixturesCollector.collect(knockoutMatches)

        return Standings(
                standings = currentStandings,
                knockoutStandings = knockoutStandings
        )
    }

    fun createGroupStandingsFromMatches(matches: List<Match>) : List<LeagueTable> {
        val groups = groupCreator.create()

        return leagueTableCalculator.calculate(matches, groups)
    }
}