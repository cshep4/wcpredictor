package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.constant.RoundConstants.A
import com.cshep4.wcpredictor.constant.RoundConstants.B
import com.cshep4.wcpredictor.constant.RoundConstants.C
import com.cshep4.wcpredictor.constant.RoundConstants.D
import com.cshep4.wcpredictor.constant.RoundConstants.E
import com.cshep4.wcpredictor.constant.RoundConstants.F
import com.cshep4.wcpredictor.constant.RoundConstants.FINAL_GROUP_MATCHDAY
import com.cshep4.wcpredictor.constant.RoundConstants.G
import com.cshep4.wcpredictor.constant.RoundConstants.H
import com.cshep4.wcpredictor.data.GroupPredictions
import com.cshep4.wcpredictor.data.Match
import org.springframework.stereotype.Component

@Component
class GroupFixturesCollector {
    fun collect(matches: List<Match>) : List<GroupPredictions> {
        val groupMatches = matches.filter { it.matchday <= FINAL_GROUP_MATCHDAY }

        val groupA = GroupPredictions(
                group = A,
                matches = groupMatches.filter { it.group == A }
        )

        val groupB = GroupPredictions(
                group = B,
                matches = groupMatches.filter { it.group == B }
        )

        val groupC = GroupPredictions(
                group = C,
                matches = groupMatches.filter { it.group == C }
        )

        val groupD = GroupPredictions(
                group = D,
                matches = groupMatches.filter { it.group == D }
        )

        val groupE = GroupPredictions(
                group = E,
                matches = groupMatches.filter { it.group == E }
        )

        val groupF = GroupPredictions(
                group = F,
                matches = groupMatches.filter { it.group == F }
        )

        val groupG = GroupPredictions(
                group = G,
                matches = groupMatches.filter { it.group == G }
        )

        val groupH = GroupPredictions(
                group = H,
                matches = groupMatches.filter { it.group == H }
        )

        return listOf(groupA, groupB, groupC, groupD, groupE, groupF, groupG, groupH)
    }
}