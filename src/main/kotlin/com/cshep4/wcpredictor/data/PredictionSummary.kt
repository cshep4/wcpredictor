package com.cshep4.wcpredictor.data

data class GroupPredictions(val group: Char = 'A', val matches: List<Match> = emptyList())

data class PredictionSummary(
        val group: List<GroupPredictions> = emptyList(),
        val knockout: List<KnockoutStandings> = emptyList()
)