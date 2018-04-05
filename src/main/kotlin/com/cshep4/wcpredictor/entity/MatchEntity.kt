package com.cshep4.wcpredictor.entity

import com.cshep4.wcpredictor.data.Match
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Match")
data class MatchEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        var hTeam: String = "",
        var aTeam: String = "",
        var hGoals: Int? = null,
        var aGoals: Int? = null,
        var played: Int = 0,
        var matchGroup: Char? = null,
        var dateTime: LocalDateTime? = null,
        var matchday: Int = 0
){
    fun toDto(): Match = Match(
            id = this.id,
            hTeam = this.hTeam,
            aTeam = this.aTeam,
            hGoals = this.hGoals,
            aGoals = this.aGoals,
            played = this.played,
            matchGroup = this.matchGroup,
            dateTime = this.dateTime,
            matchday = this.matchday)

    companion object {
        fun fromDto(dto: Match) = MatchEntity(
                id = dto.id,
                hTeam = dto.hTeam,
                aTeam = dto.aTeam,
                hGoals = dto.hGoals,
                aGoals = dto.aGoals,
                played = dto.played,
                matchGroup = dto.matchGroup,
                dateTime = dto.dateTime,
                matchday = dto.matchday)
    }
}