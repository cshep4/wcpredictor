package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.score.LeagueTableScoreCalculator
import com.cshep4.wcpredictor.component.score.MatchScoreCalculator
import com.cshep4.wcpredictor.component.score.WinnerScoreCalculator
import com.cshep4.wcpredictor.entity.UserEntity
import com.cshep4.wcpredictor.repository.PredictedMatchRepository
import com.cshep4.wcpredictor.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserScoreService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var predictedMatchRepository: PredictedMatchRepository

    @Autowired
    private lateinit var leagueTableScoreCalculator: LeagueTableScoreCalculator

    @Autowired
    private lateinit var matchScoreCalculator: MatchScoreCalculator

    @Autowired
    private lateinit var winnerScoreCalculator: WinnerScoreCalculator

    fun updateScores() {
        var users = userRepository.findAll().map { it.toDto() }
        users.forEach { it.score = 0 }

        val predictedMatches = predictedMatchRepository.getAllMatchesWithPredictions().map { it.toDto() }

        if (!predictedMatches.none { it.hGoals != null && it.aGoals != null }) {
            users = leagueTableScoreCalculator.calculate(users, predictedMatches)
            users = matchScoreCalculator.calculate(users, predictedMatches)
            users = winnerScoreCalculator.calculate(users)
        }

        userRepository.saveAll(users.map { UserEntity.fromDto(it) })
    }
}