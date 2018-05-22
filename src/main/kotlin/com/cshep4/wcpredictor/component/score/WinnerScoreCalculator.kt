package com.cshep4.wcpredictor.component.score

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.User
import com.cshep4.wcpredictor.repository.FixturesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WinnerScoreCalculator {
    companion object {
        const val WINNER_ADDITION = 20
    }

    @Autowired
    private lateinit var fixturesRepository: FixturesRepository

    fun calculate(users: List<User>): List<User> {
        val final = fixturesRepository.getFinal().toDto()

        if (final.hGoals == null || final.aGoals == null) {
            return users
        }

        val winner = getWinnersName(final)

        users.filter { it.predictedWinner == winner }
                .forEach { it.score += WINNER_ADDITION }

        return users
    }

    private fun getWinnersName(final: Match): String {
        return if (final.hGoals!! > final.aGoals!!) {
            final.hTeam
        } else {
            final.aTeam
        }
    }
}