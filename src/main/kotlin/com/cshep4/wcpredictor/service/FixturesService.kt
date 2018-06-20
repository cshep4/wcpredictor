package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.fixtures.FixtureFormatter
import com.cshep4.wcpredictor.component.fixtures.FixturesByDate
import com.cshep4.wcpredictor.component.fixtures.OverrideMatchScore
import com.cshep4.wcpredictor.component.fixtures.PredictionMerger
import com.cshep4.wcpredictor.constant.APIConstants.API_KEY
import com.cshep4.wcpredictor.constant.APIConstants.API_URL
import com.cshep4.wcpredictor.constant.APIConstants.HEADER_KEY
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.PredictedMatch
import com.cshep4.wcpredictor.repository.FixturesRepository
import com.cshep4.wcpredictor.service.fixtures.FixturesApiService
import com.cshep4.wcpredictor.service.fixtures.UpdateFixturesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class FixturesService {
    @Autowired
    private lateinit var fixtureApiService: FixturesApiService

    @Autowired
    private lateinit var fixtureFormatter: FixtureFormatter

    @Autowired
    private lateinit var updateFixturesService: UpdateFixturesService

    @Autowired
    private lateinit var fixturesRepository: FixturesRepository

    @Autowired
    private lateinit var predictionMerger: PredictionMerger

    @Autowired
    private lateinit var predictionsService: PredictionsService

    @Autowired
    private lateinit var overrideMatchService: OverrideMatchService

    @Autowired
    private lateinit var overrideMatchScore: OverrideMatchScore

    @Autowired
    private lateinit var fixturesByDate: FixturesByDate

    fun update(): List<Match> {
        val apiResult = fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY) ?: return emptyList()

        val matches = fixtureFormatter.format(apiResult)

        val overrides = overrideMatchService.retrieveAllOverriddenMatches()

        val finalScores = overrideMatchScore.update(matches, overrides)

        return updateFixturesService.update(finalScores)
    }

    fun retrieveAllMatches() : List<Match> = fixturesRepository.findAll().map { it.toDto() }

    fun retrieveAllPredictedMatchesByUserId(id: Long) : List<Match> = fixturesRepository.findPredictedMatchesByUserId(id).map { it.toDto() }

    fun retrieveAllMatchesWithPredictions(id: Long) : List<PredictedMatch> {
        val matches = retrieveAllMatches()

        if (matches.isEmpty()) {
            return emptyList()
        }

        val predictions = predictionsService.retrievePredictionsByUserId(id)

        return predictionMerger.merge(matches, predictions)
    }

    fun retrieveAllUpcomingFixtures() : Map<LocalDate, List<Match>> {
        val upcomingMatches = retrieveAllMatches().filter { it.dateTime!!.isAfter(LocalDateTime.now(Clock.systemUTC())) }

        return if (upcomingMatches.isEmpty()) {
            emptyMap()
        } else {
            fixturesByDate.format(upcomingMatches)
        }
    }
}