package com.cshep4.wcpredictor.entity

import com.cshep4.wcpredictor.data.League
import javax.persistence.*

@Entity
@Table(name = "League")
data class LeagueEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val name: String = ""
){
    fun toDto(): League = League(
            id = this.id,
            name = this.name)

    companion object {
        fun fromDto(dto: League) = LeagueEntity(
                id = dto.id,
                name = dto.name)
    }
}
