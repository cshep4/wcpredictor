package com.cshep4.wcpredictor.component.leaguetable

import com.cshep4.wcpredictor.constant.TeamGroups.NUMBER_OF_GROUPS
import com.cshep4.wcpredictor.constant.TeamGroups.NUMBER_OF_TEAMS_PER_GROUP
import com.cshep4.wcpredictor.constant.TeamGroups.groupMap
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

internal class GroupCreatorTest {
    private val groupCreator = GroupCreator()

    @Test
    fun `'create' creates list of league table objects that contain each team without any match data`() {
        val groups = groupCreator.create()

        assertThat(groups.size, `is`(NUMBER_OF_GROUPS))

        groups.forEach {
            assertThat(it.table.size, `is`(NUMBER_OF_TEAMS_PER_GROUP))

            val group = it.group
            it.table.forEach {
                assertThat(groupMap[it.teamName], `is`(group))
                assertThat(it.rank, `is`(0))
                assertThat(it.played, `is`(0))
                assertThat(it.points, `is`(0))
                assertThat(it.wins, `is`(0))
                assertThat(it.draws, `is`(0))
                assertThat(it.losses, `is`(0))
                assertThat(it.goalsFor, `is`(0))
                assertThat(it.goalsAgainst, `is`(0))
                assertThat(it.goalDifference, `is`(0))
            }
        }
    }

}