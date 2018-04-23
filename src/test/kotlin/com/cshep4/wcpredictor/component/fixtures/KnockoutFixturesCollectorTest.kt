package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.constant.RoundConstants.FINAL
import com.cshep4.wcpredictor.constant.RoundConstants.LAST_16
import com.cshep4.wcpredictor.constant.RoundConstants.QUARTER_FINAL
import com.cshep4.wcpredictor.constant.RoundConstants.SEMI_FINAL
import com.cshep4.wcpredictor.constant.RoundConstants.THIRD_PLACE_PLAY_OFF
import com.cshep4.wcpredictor.data.Match
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

internal class KnockoutFixturesCollectorTest {
    private val knockoutFixturesCollector = KnockoutFixturesCollector()

    @Test
    fun `'collect' removes all group matches and organises knockout matches into correct rounds`() {
        val groupMatch1 = Match(matchday = 1)
        val groupMatch2 = Match(matchday = 2)
        val groupMatch3 = Match(matchday = 3)
        val last16 = Match(matchday = 4)
        val quarterFinal = Match(matchday = 5)
        val semiFinal = Match(matchday = 6)
        val thirdPlacePlayOff = Match(matchday = 7)
        val final = Match(matchday = 8)

        val matches = listOf(groupMatch1, groupMatch2, groupMatch3, last16, quarterFinal, semiFinal, thirdPlacePlayOff, final)

        val result = knockoutFixturesCollector.collect(matches)

        assertThat(result[0].round, `is`(LAST_16))
        assertThat(result[0].matches[0], `is`(last16))
        assertThat(result[1].round, `is`(QUARTER_FINAL))
        assertThat(result[1].matches[0], `is`(quarterFinal))
        assertThat(result[2].round, `is`(SEMI_FINAL))
        assertThat(result[2].matches[0], `is`(semiFinal))
        assertThat(result[3].round, `is`(THIRD_PLACE_PLAY_OFF))
        assertThat(result[3].matches[0], `is`(thirdPlacePlayOff))
        assertThat(result[4].round, `is`(FINAL))
        assertThat(result[4].matches[0], `is`(final))

        result.forEach {
            assertThat(it.matches.contains(groupMatch1), `is`(false))
            assertThat(it.matches.contains(groupMatch2), `is`(false))
            assertThat(it.matches.contains(groupMatch3), `is`(false))
        }
    }

}