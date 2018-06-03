package com.cshep4.wcpredictor.component.score

import com.cshep4.wcpredictor.constant.RoundConstants.FINAL_GROUP_MATCHDAY
import com.cshep4.wcpredictor.data.*
import com.cshep4.wcpredictor.service.LeagueTableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LeagueTableScoreCalculator {
    companion object {
        const val SCORE_ADDITION = 5
    }

    @Autowired
    private lateinit var leagueTableService: LeagueTableService

    fun calculate(users: List<User>, predictedMatches: List<MatchPredictionResult>): List<User> {
        if (isGroupStageFinished(predictedMatches)) {
            val standings = leagueTableService.getCurrentStandings()

            users.forEach { updateScore(it, standings, predictedMatches) }
        }

        return users
    }

    private fun isGroupStageFinished(predictedMatches: List<MatchPredictionResult>): Boolean {
        val predictedGroupMatches = predictedMatches.filter { it.matchday <= FINAL_GROUP_MATCHDAY }

        return predictedGroupMatches.none{ it.hGoals == null && it.aGoals == null }
    }

    private fun updateScore(user: User, standings: Standings, predictedMatches: List<MatchPredictionResult>) {
        val usersPredictedMatches = predictedMatches
                .filter { it.userId == user.id }
                .filter { it.matchday <= FINAL_GROUP_MATCHDAY }
                .map { it.toPredictedMatch() }

        val predictedStandings = leagueTableService.createGroupStandingsFromMatches(usersPredictedMatches)

        var i = 0
        predictedStandings.forEach {
            user.score += getUserScoreForGroupPrediction(it, standings, i)
            i++
        }
    }

    private fun getUserScoreForGroupPrediction(predictedGroup: LeagueTable, standings: Standings, i: Int): Int {
        var j = 0
        var score = 0
        predictedGroup.table.forEach {
            if (isTablePositionCorrect(it, standings, i, j)) {
                score += SCORE_ADDITION
            }
            j++
        }

        return score
    }

    private fun isTablePositionCorrect(predictedTableTeam: TableTeam, standings: Standings, i: Int, j: Int) =
            predictedTableTeam.teamName == standings.standings[i].table[j].teamName
}