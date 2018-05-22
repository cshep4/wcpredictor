package com.cshep4.wcpredictor.component.score

import com.cshep4.wcpredictor.entity.MatchPredictionResultEntity
import com.cshep4.wcpredictor.entity.UserEntity
import com.cshep4.wcpredictor.repository.PredictedMatchRepository
import com.cshep4.wcpredictor.repository.UserRepository
import com.cshep4.wcpredictor.service.UserScoreService
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class UserScoreServiceTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var predictedMatchRepository: PredictedMatchRepository

    @Mock
    private lateinit var leagueTableScoreCalculator: LeagueTableScoreCalculator

    @Mock
    private lateinit var matchScoreCalculator: MatchScoreCalculator

    @Mock
    private lateinit var winnerScoreCalculator: WinnerScoreCalculator

    @InjectMocks
    private lateinit var userScoreService: UserScoreService

    @Test
    fun `'updateScores' will get a list of users, add score for individual matches, then group position, then winner and save back to db`() {
        val userEntities = listOf(UserEntity())
        val users = userEntities.map { it.toDto() }

        val predictedMatchEntities = listOf(MatchPredictionResultEntity())
        val predictedMatches = predictedMatchEntities.map { it.toDto() }

        whenever(userRepository.findAll()).thenReturn(userEntities)
        whenever(predictedMatchRepository.getAllMatchesWithPredictions()).thenReturn(predictedMatchEntities)
        whenever(leagueTableScoreCalculator.calculate(users, predictedMatches)).thenReturn(users)
        whenever(matchScoreCalculator.calculate(users, predictedMatches)).thenReturn(users)
        whenever(winnerScoreCalculator.calculate(users)).thenReturn(users)

        userScoreService.updateScores()

        verify(leagueTableScoreCalculator).calculate(users, predictedMatches)
        verify(matchScoreCalculator).calculate(users, predictedMatches)
        verify(winnerScoreCalculator).calculate(users)
        verify(userRepository).saveAll(userEntities)
    }

}