package com.cshep4.wcpredictor.service.standings.join

import com.cshep4.wcpredictor.data.UserLeague
import com.cshep4.wcpredictor.entity.UserLeagueEntity
import com.cshep4.wcpredictor.repository.UserLeagueRepository
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
internal class JoinLeagueServiceTest {
    @Mock
    private lateinit var userLeagueRepository: UserLeagueRepository

    @InjectMocks
    private lateinit var joinLeagueService: JoinLeagueService

    @Test
    fun `'joinLeague' adds userLeague record to db`() {
        val userLeague = UserLeague()
        val userLeagueEntity = UserLeagueEntity.fromDto(userLeague)

        whenever(userLeagueRepository.save(userLeagueEntity)).thenReturn(userLeagueEntity)

        val result = joinLeagueService.joinLeague(userLeague)

        verify(userLeagueRepository).save(userLeagueEntity)
        assertThat(result, `is`(userLeague))
    }
}