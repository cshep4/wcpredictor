package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.data.OverrideMatch
import com.cshep4.wcpredictor.repository.OverrideMatchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OverrideMatchService {
    @Autowired
    private lateinit var overrideMatchRepository: OverrideMatchRepository

    fun retrieveAllOverriddenMatches() : List<OverrideMatch> = overrideMatchRepository.findAll()
            .filter { it.hGoals != null && it.aGoals != null }
            .map { it.toDto() }
}