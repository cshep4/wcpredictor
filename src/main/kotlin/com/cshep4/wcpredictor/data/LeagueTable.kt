package com.cshep4.wcpredictor.data

data class TableTeam(
        var rank: Int = 0,
        val teamName: String = "",
        var played: Int = 0,
        var points: Int = 0,
        var wins: Int = 0,
        var draws: Int = 0,
        var losses: Int = 0,
        var goalsFor: Int = 0,
        var goalsAgainst: Int = 0,
        var goalDifference: Int = 0
)

data class LeagueTable(
        val group: Char = 'A',
        var table: MutableList<TableTeam> = mutableListOf()
)

data class KnockoutStandings(
        val round: String = "",
        val matches: List<Match> = emptyList()
)

data class Standings(
        val standings: List<LeagueTable> = emptyList(),
        val knockoutStandings: List<KnockoutStandings> = emptyList()
)