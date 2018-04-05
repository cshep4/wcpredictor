package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.constant.APIConstants.API_KEY
import com.cshep4.wcpredictor.constant.APIConstants.API_URL
import com.cshep4.wcpredictor.constant.APIConstants.HEADER_KEY
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.repository.FixturesRepository
import com.cshep4.wcpredictor.service.fixtures.FixturesApiService
import com.cshep4.wcpredictor.service.fixtures.FormatFixturesService
import com.cshep4.wcpredictor.service.fixtures.UpdateFixturesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FixturesService {
    @Autowired
    private lateinit var fixtureApiService: FixturesApiService

    @Autowired
    private lateinit var formatFixturesService: FormatFixturesService

    @Autowired
    private lateinit var updateFixturesService: UpdateFixturesService

    @Autowired
    private lateinit var fixturesRepository: FixturesRepository

    fun update(): List<Match> {
        val apiResult = fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY) ?: return emptyList()

        val matches = formatFixturesService.format(apiResult)

        return updateFixturesService.update(matches)
    }

    fun retrieveAllMatches() : List<Match> = fixturesRepository.findAll().map { it.toDto() }
}