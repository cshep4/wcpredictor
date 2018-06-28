package com.cshep4.wcpredictor.component.leaguetable

import com.cshep4.wcpredictor.constant.TeamGroups.fairPlay
import com.cshep4.wcpredictor.data.LeagueTable
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.TableTeam
import com.cshep4.wcpredictor.extension.getGroupTable
import org.springframework.stereotype.Component

@Component
class LeagueTableCalculator {
    fun calculate(matches: List<Match>, groups: List<LeagueTable>) : List<LeagueTable> {
        val leagueTable = groups.toMutableList()

        matches.filter { it.hGoals != null && it.aGoals != null }
                .forEach { updateTableForMatch(it, leagueTable) }

        setFairPlayOverride(leagueTable)

        sortTables(leagueTable)

        updateRank(leagueTable)

        return leagueTable
    }

    private fun setFairPlayOverride(leagueTable: MutableList<LeagueTable>) {
        leagueTable.forEach {
            it.table.forEach {
                it.fairPlay = if (fairPlay.contains(it.teamName)) {
                    1
                } else {
                    0
                }
            }
        }
    }

    private fun updateRank(leagueTable: MutableList<LeagueTable>) {
        leagueTable.forEach {
            var i = 1
            it.table.forEach { it.rank = i++ }
        }
    }

    private fun sortTables(leagueTable: MutableList<LeagueTable>) {
        leagueTable.forEach {
            it.table = ArrayList(it.table).sortedWith(
                    compareByDescending<TableTeam>{it.points}
                    .thenByDescending { it.goalDifference }
                    .thenByDescending { it.goalsFor }
                    .thenByDescending { it.fairPlay })
                    .toMutableList()
        }
    }

    private fun updateTableForMatch(match: Match, leagueTable: MutableList<LeagueTable>) {
        leagueTable.getGroupTable(match.group!!)
                .forEach { updateTableTeam(it, match)}
    }

    private fun updateTableTeam(tableTeam: TableTeam, match: Match) {
        if (tableTeam.teamName == match.hTeam) {
            updateTeamStats(tableTeam, match.hGoals!!, match.aGoals!!)
        } else if (tableTeam.teamName == match.aTeam) {
            updateTeamStats(tableTeam, match.aGoals!!, match.hGoals!!)
        }
    }

    private fun updateTeamStats(tableTeam: TableTeam, goalsFor: Int, goalsAgainst: Int) {
        when {
            goalsFor > goalsAgainst -> {
                tableTeam.wins += 1
                tableTeam.points += 3
            }
            goalsFor == goalsAgainst -> {
                tableTeam.draws += 1
                tableTeam.points += 1
            }
            else -> tableTeam.losses += 1
        }
        tableTeam.played += 1
        tableTeam.goalsFor += goalsFor
        tableTeam.goalsAgainst += goalsAgainst
        tableTeam.goalDifference = tableTeam.goalsFor - tableTeam.goalsAgainst
    }
}