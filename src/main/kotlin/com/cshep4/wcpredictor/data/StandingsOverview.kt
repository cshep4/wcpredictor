package com.cshep4.wcpredictor.data

data class StandingsOverview(val overallLeagueOverview: OverallLeagueOverview = OverallLeagueOverview(),
                             val userLeagues: List<UserLeagueOverview> = emptyList())