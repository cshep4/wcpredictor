package com.cshep4.wcpredictor.component.score

import com.cshep4.wcpredictor.data.User
import com.cshep4.wcpredictor.entity.MatchEntity
import com.cshep4.wcpredictor.repository.FixturesRepository
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class WinnerScoreCalculatorTest {
    companion object {
        const val TEAM_ONE = "Team 1"
        const val TEAM_TWO = "Team 2"
        const val WINNER_ADDITION = 20
    }

    @Mock
    private lateinit var fixturesRepository: FixturesRepository

    @InjectMocks
    private lateinit var winnerScoreCalculator: WinnerScoreCalculator

    private val users = listOf(
            User(id = 1, score = 0, predictedWinner = TEAM_ONE),
            User(id = 2, score = 0, predictedWinner = TEAM_TWO),
            User(id = 3, score = 0, predictedWinner = TEAM_ONE)
    )

    @Test
    fun `'calculate' checks if final has been played and does nothing if not`() {
        val final = MatchEntity(hGoals = null, aGoals = null, hTeam = TEAM_ONE, aTeam = TEAM_TWO)
        whenever(fixturesRepository.getFinal()).thenReturn(final)

        val result = winnerScoreCalculator.calculate(users)

        assertThat(result[0].score, `is`(0))
        assertThat(result[1].score, `is`(0))
        assertThat(result[2].score, `is`(0))
    }

    @Test
    fun `'calculate' loops through users and adds 20 to their score if the final winner matches their prediction`() {
        val final = MatchEntity(hGoals = 2, aGoals = 0, hTeam = TEAM_ONE, aTeam = TEAM_TWO)
        whenever(fixturesRepository.getFinal()).thenReturn(final)

        val result = winnerScoreCalculator.calculate(users)

        assertThat(result[0].score, `is`(WINNER_ADDITION))
        assertThat(result[1].score, `is`(0))
        assertThat(result[2].score, `is`(WINNER_ADDITION))
    }

}