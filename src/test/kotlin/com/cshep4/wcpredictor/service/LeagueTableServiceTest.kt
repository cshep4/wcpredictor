package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.fixtures.KnockoutFixturesCollector
import com.cshep4.wcpredictor.component.leaguetable.GroupCreator
import com.cshep4.wcpredictor.component.leaguetable.LeagueTableCalculator
import com.cshep4.wcpredictor.data.KnockoutStandings
import com.cshep4.wcpredictor.data.LeagueTable
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.PredictedMatch
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

    @Mock
    private lateinit var knockoutFixturesCollector: KnockoutFixturesCollector

    @InjectMocks
    private lateinit var leagueTableService: LeagueTableService

    @Test
    fun `'getCurrentStandings' retrieves matches, creates each group and returns standings`() {
        val matches = listOf(Match(matchday = 1), Match(matchday = 5))
        val groupMatches = listOf(Match(matchday = 1))
        val knockoutMatches = listOf(Match(matchday = 5))
        val groups = listOf(LeagueTable())
        val knockoutStandings = listOf(KnockoutStandings())

        whenever(fixturesService.retrieveAllMatches()).thenReturn(matches)
        whenever(groupCreator.create()).thenReturn(groups)
        whenever(leagueTableCalculator.calculate(groupMatches, groups)).thenReturn(groups)
        whenever(knockoutFixturesCollector.collect(knockoutMatches)).thenReturn(knockoutStandings)

        val result = leagueTableService.getCurrentStandings()

        assertThat(result.standings, `is`(groups))
        assertThat(result.knockoutStandings, `is`(knockoutStandings))
    }

    @Test
    fun `'getPredictedStandings' retrieves match predictions, creates each group and returns standings`() {
        val matches = listOf(PredictedMatch(matchday = 1), PredictedMatch(matchday = 5))
        val groupMatches = listOf(Match(matchday = 1))
        val knockoutMatches = listOf(Match(matchday = 5))
        val groups = listOf(LeagueTable())
        val knockoutStandings = listOf(KnockoutStandings())

        whenever(fixturesService.retrieveAllMatchesWithPredictions(1)).thenReturn(matches)
        whenever(groupCreator.create()).thenReturn(groups)
        whenever(leagueTableCalculator.calculate(groupMatches, groups)).thenReturn(groups)
        whenever(knockoutFixturesCollector.collect(knockoutMatches)).thenReturn(knockoutStandings)

        val result = leagueTableService.getPredictedStandings(1)

        assertThat(result.standings, `is`(groups))
        assertThat(result.knockoutStandings, `is`(knockoutStandings))
    }
}