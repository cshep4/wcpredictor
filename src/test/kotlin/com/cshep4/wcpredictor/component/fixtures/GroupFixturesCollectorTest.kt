package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.constant.RoundConstants.A
import com.cshep4.wcpredictor.constant.RoundConstants.B
import com.cshep4.wcpredictor.data.Match
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

internal class GroupFixturesCollectorTest {
    private val groupFixturesCollector = GroupFixturesCollector()

    @Test
    fun `'collect' organises all group matches and removes knockout matches`() {
        val groupMatch1 = Match(group = A, matchday = 1)
        val groupMatch2 = Match(group = A, matchday = 2)
        val groupMatch3 = Match(group = B, matchday = 3)
        val last16 = Match(matchday = 4)
        val quarterFinal = Match(matchday = 5)
        val semiFinal = Match(matchday = 6)
        val thirdPlacePlayOff = Match(matchday = 7)
        val final = Match(matchday = 8)

        val matches = listOf(groupMatch1, groupMatch2, groupMatch3, last16, quarterFinal, semiFinal, thirdPlacePlayOff, final)

        val result = groupFixturesCollector.collect(matches)

        MatcherAssert.assertThat(result[0].group, CoreMatchers.`is`(A))
        MatcherAssert.assertThat(result[0].matches[0], CoreMatchers.`is`(groupMatch1))
        MatcherAssert.assertThat(result[0].matches[1], CoreMatchers.`is`(groupMatch2))
        MatcherAssert.assertThat(result[1].group, CoreMatchers.`is`(B))
        MatcherAssert.assertThat(result[1].matches[0], CoreMatchers.`is`(groupMatch3))

        result.forEach {
            MatcherAssert.assertThat(it.matches.contains(last16), CoreMatchers.`is`(false))
            MatcherAssert.assertThat(it.matches.contains(quarterFinal), CoreMatchers.`is`(false))
            MatcherAssert.assertThat(it.matches.contains(semiFinal), CoreMatchers.`is`(false))
            MatcherAssert.assertThat(it.matches.contains(thirdPlacePlayOff), CoreMatchers.`is`(false))
            MatcherAssert.assertThat(it.matches.contains(final), CoreMatchers.`is`(false))
        }
    }

}