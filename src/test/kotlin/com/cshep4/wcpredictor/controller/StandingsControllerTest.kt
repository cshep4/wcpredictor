package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.StandingsOverview
import com.cshep4.wcpredictor.service.StandingsService
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus.OK

@RunWith(MockitoJUnitRunner::class)
internal class StandingsControllerTest {
    @Mock
    lateinit var standingsService: StandingsService

    @InjectMocks
    lateinit var standingsController: StandingsController

    @Test
    fun `'getUsersLeagueList' returns OK and standings overview`() {
        val standingsOverview = StandingsOverview()

        whenever(standingsService.retrieveStandingsOverview(1)).thenReturn(standingsOverview)

        val result = standingsController.getUsersLeagueList(1)

        assertThat(result.statusCode, `is`(OK))
        assertThat(result.body, `is`(standingsOverview))
    }
}