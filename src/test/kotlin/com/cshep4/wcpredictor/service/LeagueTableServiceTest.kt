package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.leaguetable.GroupCreator
import com.cshep4.wcpredictor.component.leaguetable.LeagueTableCalculator
import com.cshep4.wcpredictor.data.LeagueTable
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.Standings
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class LeagueTableServiceTest {
    @Mock
    private lateinit var fixturesService: FixturesService

    @Mock
    private lateinit var groupCreator: GroupCreator

    @Mock
    private lateinit var leagueTableCalculator: LeagueTableCalculator

    @InjectMocks
    private lateinit var leagueTableService: LeagueTableService

    @Test
    fun `'getCurrentStandings' retrieves matches, creates each group and returns standings`() {
        val matches = listOf(Match())
        val groups = listOf(LeagueTable())

        whenever(fixturesService.retrieveAllMatches()).thenReturn(matches)
        whenever(groupCreator.create()).thenReturn(groups)
        whenever(leagueTableCalculator.calculate(matches, groups)).thenReturn(groups)

        val result = leagueTableService.getCurrentStandings()

        val expectedStandings = Standings(groups)

        assertThat(result, `is`(expectedStandings))
    }

    @Test
    fun `'getPredictedStandings' retrieves match predictions, creates each group and returns standings`() {
        val matches = listOf(Match())
        val groups = listOf(LeagueTable())

        whenever(fixturesService.retrieveAllPredictedMatchesByUserId(1)).thenReturn(matches)
        whenever(groupCreator.create()).thenReturn(groups)
        whenever(leagueTableCalculator.calculate(matches, groups)).thenReturn(groups)

        val result = leagueTableService.getPredictedStandings(1)

        val expectedStandings = Standings(groups)

        assertThat(result, `is`(expectedStandings))
    }
}