package com.cshep4.wcpredictor.component.leaguetable

import com.cshep4.wcpredictor.constant.TeamGroups.groupMap
import com.cshep4.wcpredictor.data.LeagueTable
import com.cshep4.wcpredictor.data.TableTeam
import com.cshep4.wcpredictor.extension.groupDoesNotExist
import org.springframework.stereotype.Component

@Component
class GroupCreator {
    fun create() : List<LeagueTable> {
        val groups = mutableListOf<LeagueTable>()
        groupMap.forEach { teamName, groupName -> addGroupAndTeam(teamName, groupName, groups) }
        return groups
    }

    private fun addGroupAndTeam(teamName: String, groupName: Char, groups: MutableList<LeagueTable>) {
        addGroupIfDoesNotExist(groupName, groups)
        addTeamToGroup(groupName, teamName, groups)
    }

    private fun addGroupIfDoesNotExist(groupName: Char, groups: MutableList<LeagueTable>) {
        if (groups.groupDoesNotExist(groupName)) {
            groups.add(LeagueTable(group = groupName))
        }
    }

    private fun addTeamToGroup(groupName: Char, teamName: String, groups: MutableList<LeagueTable>) {
        groups.stream()
                .filter { it.group == groupName }
                .findFirst()
                .get()
                .table
                .add(TableTeam(teamName = teamName))
    }
}