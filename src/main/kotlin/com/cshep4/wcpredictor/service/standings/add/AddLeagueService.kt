package com.cshep4.wcpredictor.service.standings.add

import com.cshep4.wcpredictor.data.League
import com.cshep4.wcpredictor.entity.LeagueEntity
import com.cshep4.wcpredictor.repository.LeagueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddLeagueService {
    @Autowired
    private lateinit var leagueRepository: LeagueRepository

    fun addLeagueToDb(name: String): League {
        val leagueEntity = LeagueEntity(name = name)

        return leagueRepository.save(leagueEntity).toDto()
    }
}