package com.cshep4.wcpredictor.data.api

import com.cshep4.wcpredictor.constant.TeamGroups
import com.cshep4.wcpredictor.data.Match
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class FixturesApiResult(val _links: ResultLinks? = null,
                             val count:Int = 0,
                             val fixtures:Array<Fixture>? = null) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FixturesApiResult

        if (_links != other._links) return false
        if (count != other.count) return false
        if (!Arrays.equals(fixtures, other.fixtures)) return false

        return true
    }
    override fun hashCode(): Int {
        var result = _links?.hashCode() ?: 0
        result = 31 * result + count
        result = 31 * result + Arrays.hashCode(fixtures)
        return result
    }
}

data class Fixture(val _links: ResultLinks? = null,
                   val date:String = "",
                   val status:String = "",
                   val matchday:Int = -1,
                   val homeTeamName:String = "",
                   val awayTeamName:String = "",
                   val result: MatchResult? = null,
                   val odds: Odds? = null) {

    fun toMatch(id: Long): Match = Match(
            id = id,
            hTeam = this.homeTeamName,
            aTeam = this.awayTeamName,
            hGoals = this.result?.goalsHomeTeam,
            aGoals = this.result?.goalsAwayTeam,
            played = getPlayed(),
            group = getGroup(),
            dateTime = LocalDateTime.parse(this.date, DateTimeFormatter.ISO_DATE_TIME),
            matchday = this.matchday)

    private fun getPlayed(): Int {
        return when {
            result?.goalsHomeTeam == null -> 0
            result.goalsAwayTeam == null -> 0
            else -> 1
        }
    }

    private fun getGroup() : Char? {
        return when {
            matchday <= 3 -> TeamGroups.groupMap[homeTeamName]
            else -> null
        }
    }
}

data class MatchResult(val goalsHomeTeam:Int? = null,
                       val goalsAwayTeam:Int? = null,
                       val halfTime: MatchResult? = null)

data class Odds(val homeWin:Double = 0.0,
                val draw:Double = 0.0,
                val awayWin:Double = 0.0)
