package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.repository.UserRepository
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class ScoreServiceTest {
    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var scoreService: ScoreService

    @Test
    fun `'retrieveScoreAndRank' should get user scores and ranks from db and return current users`() {
        val userRanks = listOf(arrayOf(1L, 1L, 4L), arrayOf(2L, 2L, 3L), arrayOf(3L, 3L, 1L))

        whenever(userRepository.getUserRankAndScore()).thenReturn(userRanks)

        val result = scoreService.retrieveScoreAndRank(1)

        assertThat(result.id, `is`(1L))
        assertThat(result.rank, `is`(1L))
        assertThat(result.score, `is`(4))
    }

}