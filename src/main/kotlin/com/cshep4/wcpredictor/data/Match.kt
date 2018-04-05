package com.cshep4.wcpredictor.data

import java.time.LocalDateTime

data class Match (
        val id: Long = 0,
        var hTeam: String = "",
        var aTeam: String = "",
        var hGoals: Int? = null,
        var aGoals: Int? = null,
        var played: Int = 0,
        var matchGroup: Char? = null,
        var dateTime: LocalDateTime? = null,
        var matchday: Int = 0
)