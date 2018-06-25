package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.override.MatchOverrideMerger
import com.cshep4.wcpredictor.data.MatchWithOverride
import com.cshep4.wcpredictor.data.OverrideMatch
import com.cshep4.wcpredictor.entity.OverrideMatchEntity
import com.cshep4.wcpredictor.repository.OverrideMatchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OverrideMatchService {
    @Autowired
    private lateinit var overrideMatchRepository: OverrideMatchRepository

    @Autowired
    private lateinit var fixturesService: FixturesService

    @Autowired
    private lateinit var matchOverrideMerger: MatchOverrideMerger

    fun retrieveAllOverriddenMatches() : List<OverrideMatch> = overrideMatchRepository.findAll()
            .filter { it.hGoals != null && it.aGoals != null }
            .map { it.toDto() }

    fun updateOverrides(overrides: List<OverrideMatch>): List<OverrideMatch> {
        val overrideEntities = overrides.map { OverrideMatchEntity.fromDto(it) }

        val savedOverrides = overrideMatchRepository.saveAll(overrideEntities)

        return savedOverrides.map { it.toDto() }
    }

    fun retrieveAllMatchesWithOverrideScores() : List<MatchWithOverride> {
        val matches = fixturesService.retrieveMatchesFromApi() ?: return emptyList()

        val overrides = overrideMatchRepository.findAll()
                .map { it.toDto() }

        return matchOverrideMerger.merge(matches, overrides)
    }
}