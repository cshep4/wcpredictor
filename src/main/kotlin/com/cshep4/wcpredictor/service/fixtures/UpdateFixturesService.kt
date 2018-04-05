package com.cshep4.wcpredictor.service.fixtures

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.entity.MatchEntity
import com.cshep4.wcpredictor.repository.FixturesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UpdateFixturesService {
    @Autowired
    private lateinit var fixturesRepository: FixturesRepository

    fun update(matches: List<Match>): List<Match> {
        val matchEntities = matches.map { MatchEntity.fromDto(it) }

        if (matchEntities.isEmpty()) {
            return emptyList()
        }

        return fixturesRepository.saveAll(matchEntities).map { it.toDto() }
    }
}