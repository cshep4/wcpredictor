package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.data.OverallLeagueOverview
import com.cshep4.wcpredictor.data.StandingsOverview
import com.cshep4.wcpredictor.data.UserLeagueOverview
import com.cshep4.wcpredictor.repository.UserRepository
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigInteger.valueOf

@RunWith(MockitoJUnitRunner::class)
internal class StandingsServiceTest {
    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var standingsService: StandingsService

    @Test
    fun `'retrieveStandingsOverview' gets standings overview from db and returns them`() {
        val userLeagues = listOf(arrayOf("test", valueOf(12345), valueOf(2)))
        val overallLeagueOverview = listOf(arrayOf(valueOf(1), valueOf(2), 3, valueOf(4)))

        whenever(userRepository.getUsersLeagueList(1)).thenReturn(userLeagues)
        whenever(userRepository.getOverallLeagueOverview(1)).thenReturn(overallLeagueOverview)

        val expectedUserLeagues = listOf(UserLeagueOverview("test", 12345, 2))
        val expectedOverallLeague = OverallLeagueOverview(4, 2)
        val expectedResult = StandingsOverview(expectedOverallLeague, expectedUserLeagues)

        val result = standingsService.retrieveStandingsOverview(1)

        assertThat(result, `is`(expectedResult))
    }

}