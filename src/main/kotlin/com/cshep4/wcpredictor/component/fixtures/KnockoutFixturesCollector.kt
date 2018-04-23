package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.constant.RoundConstants.FINAL
import com.cshep4.wcpredictor.constant.RoundConstants.FINAL_MATCHDAY
import com.cshep4.wcpredictor.constant.RoundConstants.LAST_16
import com.cshep4.wcpredictor.constant.RoundConstants.LAST_16_MATCHDAY
import com.cshep4.wcpredictor.constant.RoundConstants.QUARTER_FINAL
import com.cshep4.wcpredictor.constant.RoundConstants.QUARTER_FINAL_MATCHDAY
import com.cshep4.wcpredictor.constant.RoundConstants.SEMI_FINAL
import com.cshep4.wcpredictor.constant.RoundConstants.SEMI_FINAL_MATCHDAY
import com.cshep4.wcpredictor.constant.RoundConstants.THIRD_PLACE_PLAY_OFF
import com.cshep4.wcpredictor.constant.RoundConstants.THIRD_PLACE_PLAY_OFF_MATCHDAY
import com.cshep4.wcpredictor.data.KnockoutStandings
import com.cshep4.wcpredictor.data.Match
import org.springframework.stereotype.Component

@Component
class KnockoutFixturesCollector {
    fun collect(matches: List<Match>) : List<KnockoutStandings> {
        val last16 = KnockoutStandings(
                round = LAST_16,
                matches = matches.filter { it.matchday == LAST_16_MATCHDAY }
        )

        val quarterFinal = KnockoutStandings(
                round = QUARTER_FINAL,
                matches = matches.filter { it.matchday == QUARTER_FINAL_MATCHDAY }
        )

        val semiFinal = KnockoutStandings(
                round = SEMI_FINAL,
                matches = matches.filter { it.matchday == SEMI_FINAL_MATCHDAY }
        )

        val thirdPlacePlayOff = KnockoutStandings(
                round = THIRD_PLACE_PLAY_OFF,
                matches = matches.filter { it.matchday == THIRD_PLACE_PLAY_OFF_MATCHDAY }
        )

        val final = KnockoutStandings(
                round = FINAL,
                matches = matches.filter { it.matchday == FINAL_MATCHDAY }
        )

        return listOf(last16, quarterFinal, semiFinal, thirdPlacePlayOff, final)
    }
}