package com.cshep4.wcpredictor.component.score

import com.cshep4.wcpredictor.data.*
import com.cshep4.wcpredictor.service.LeagueTableService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class LeagueTableScoreCalculatorTest {
    companion object {
        const val NUM_TEAMS = 6
        const val SCORE_ADDITION = 5
    }

    @Mock
    private lateinit var leagueTableService: LeagueTableService

    @InjectMocks
    private lateinit var leagueTableScoreCalculator: LeagueTableScoreCalculator

    @Test
    fun `'calculate' gets a league table of the current scores`() {
        val users = listOf(User(id = 1))
        val predictedMatches = listOf(MatchPredictionResult(userId = 1, matchday = 4))
        val groups = listOf(LeagueTable())
        val standings = Standings(standings = groups)

        whenever(leagueTableService.getCurrentStandings()).thenReturn(standings)

        leagueTableScoreCalculator.calculate(users, predictedMatches)

        verify(leagueTableService).getCurrentStandings()

    }

    @Test
    fun `'calculate' loops through each user and creates a league table based on their predicted scores`() {
        val users = listOf(User(id = 1), User(id = 2), User(id = 3))
        val predictedMatches = listOf(MatchPredictionResult(userId = 1, matchday = 4), MatchPredictionResult(userId = 2, matchday = 4), MatchPredictionResult(userId = 3, matchday = 4))
        val groups = listOf(LeagueTable())
        val standings = Standings(standings = groups)

        whenever(leagueTableService.getCurrentStandings()).thenReturn(standings)
        whenever(leagueTableService.createGroupStandingsFromMatches(any())).thenReturn(groups)

        leagueTableScoreCalculator.calculate(users, predictedMatches)

        verify(leagueTableService, times(3)).createGroupStandingsFromMatches(any())
    }

    @Test
    fun `'calculate' loops through each user and compares their league table to the real one and awards 5 points for matching group positions, then returns the list of users`() {
        val users = listOf(User(id = 1, score = 0), User(id = 2, score = 0), User(id = 3, score = 0))
        val predictedMatches = listOf(MatchPredictionResult(userId = 1, matchday = 4), MatchPredictionResult(userId = 2, matchday = 4), MatchPredictionResult(userId = 3, matchday = 4))

        val groups1 = listOf(
                LeagueTable(group = 'A', table = mutableListOf(
                        TableTeam(teamName = "Team 1", played = 3),
                        TableTeam(teamName = "Team 2", played = 3),
                        TableTeam(teamName = "Team 3", played = 3))
                ),
                LeagueTable(group = 'B', table = mutableListOf(
                        TableTeam(teamName = "Team 5", played = 3),
                        TableTeam(teamName = "Team 6", played = 3),
                        TableTeam(teamName = "Team 7", played = 3))
                )
        )

        val groups2 = listOf(
                LeagueTable(group = 'A', table = mutableListOf(
                        TableTeam(teamName = "Team 3", played = 3),
                        TableTeam(teamName = "Team 2", played = 3),
                        TableTeam(teamName = "Team 1", played = 3))
                ),
                LeagueTable(group = 'B', table = mutableListOf(
                        TableTeam(teamName = "Team 7", played = 3),
                        TableTeam(teamName = "Team 6", played = 3),
                        TableTeam(teamName = "Team 5", played = 3))
                )
        )

        val groups3 = listOf(
                LeagueTable(group = 'A', table = mutableListOf(
                        TableTeam(teamName = "Team 2", played = 3),
                        TableTeam(teamName = "Team 3", played = 3),
                        TableTeam(teamName = "Team 1", played = 3))
                ),
                LeagueTable(group = 'B', table = mutableListOf(
                        TableTeam(teamName = "Team 7", played = 3),
                        TableTeam(teamName = "Team 5", played = 3),
                        TableTeam(teamName = "Team 6", played = 3))
                )
        )

        whenever(leagueTableService.getCurrentStandings()).thenReturn(Standings(standings = groups1))

        whenever(leagueTableService.createGroupStandingsFromMatches(any()))
                .thenReturn(groups1)
                .thenReturn(groups2)
                .thenReturn(groups3)

        val result = leagueTableScoreCalculator.calculate(users, predictedMatches)

        val allRight = NUM_TEAMS * SCORE_ADDITION
        val twoRight = 2 * SCORE_ADDITION
        val noneRight = 0

        assertThat(result[0].score, `is`(allRight))
        assertThat(result[1].score, `is`(twoRight))
        assertThat(result[2].score, `is`(noneRight))
    }

//    @Test
//    fun `'calculate' does not update if the group stage hasn't finished`() {
//        val users = listOf(User(id = 1, score = 0), User(id = 2, score = 0), User(id = 3, score = 0))
//        val predictedMatches = listOf(
//                MatchPredictionResult(userId = 1, matchday = 1, hGoals = 1, aGoals = 2),
//                MatchPredictionResult(userId = 2, matchday = 2, hGoals = 3, aGoals = 0),
//                MatchPredictionResult(userId = 3, matchday = 3, hGoals = null, aGoals = null)
//        )
//
//        leagueTableScoreCalculator.calculate(users, predictedMatches)
//
//        verify(leagueTableService, times(0)).getCurrentStandings()
//        verify(leagueTableService, times(0)).createGroupStandingsFromMatches(any())
//    }
}