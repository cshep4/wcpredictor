package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.Standings
import com.cshep4.wcpredictor.service.LeagueTableService
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus

@RunWith(MockitoJUnitRunner::class)
internal class LeagueTableControllerTest {
    @Mock
    private lateinit var leagueTableService: LeagueTableService

    @InjectMocks
    private lateinit var leagueTableController: LeagueTableController

    @Test
    fun `'getCurrentStandings' should return the current standings in the response body and status OK`() {
        val standings = Standings()

        whenever(leagueTableService.getCurrentStandings()).thenReturn(standings)

        val result = leagueTableController.getCurrentStandings()

        assertThat(result.statusCode, `is`(HttpStatus.OK))
        assertThat(result.body, `is`(standings))
    }

    @Test
    fun `'getPredictedStandings' should return the predicted standings in the response body and status OK`() {
        val standings = Standings()

        whenever(leagueTableService.getPredictedStandings(1)).thenReturn(standings)

        val result = leagueTableController.getPredictedStandings(1)

        assertThat(result.statusCode, `is`(HttpStatus.OK))
        assertThat(result.body, `is`(standings))
    }
}