package com.cshep4.wcpredictor.service.standings.join

import com.cshep4.wcpredictor.data.UserLeague
import com.cshep4.wcpredictor.entity.UserLeagueEntity
import com.cshep4.wcpredictor.repository.UserLeagueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JoinLeagueService {
    @Autowired
    private lateinit var userLeagueRepository: UserLeagueRepository

    fun joinLeague(userLeague: UserLeague) : UserLeague {
        val userLeagueEntity = UserLeagueEntity.fromDto(userLeague)

        return userLeagueRepository.save(userLeagueEntity).toDto()
    }
}