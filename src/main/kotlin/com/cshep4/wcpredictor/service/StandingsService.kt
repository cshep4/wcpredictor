package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.data.UserLeagueOverview
import com.cshep4.wcpredictor.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class StandingsService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun retrieveUsersLeagueList(id: Long) : List<UserLeagueOverview> {
        val rawData = userRepository.getUsersLeagueList(id)

        return rawData.map { UserLeagueOverview(it[0] as String, (it[1] as BigInteger).toLong(), (it[2] as BigInteger).toLong()) }
    }
}