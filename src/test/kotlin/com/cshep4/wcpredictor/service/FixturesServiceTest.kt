package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.fixtures.FixtureFormatter
import com.cshep4.wcpredictor.component.fixtures.PredictionMerger
import com.cshep4.wcpredictor.constant.APIConstants.API_KEY
import com.cshep4.wcpredictor.constant.APIConstants.API_URL
import com.cshep4.wcpredictor.constant.APIConstants.HEADER_KEY
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.data.api.FixturesApiResult
import com.cshep4.wcpredictor.entity.MatchEntity
import com.cshep4.wcpredictor.repository.FixturesRepository
import com.cshep4.wcpredictor.service.fixtures.FixturesApiService
import com.cshep4.wcpredictor.service.fixtures.UpdateFixturesService
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(MockitoJUnitRunner::class)
internal class FixturesServiceTest {
    @Mock
    private lateinit var fixtureApiService: FixturesApiService

    @Mock
    private lateinit var fixtureFormatter: FixtureFormatter

    @Mock
    private lateinit var updateFixturesService: UpdateFixturesService

    @Mock
    private lateinit var fixturesRepository: FixturesRepository

    @Mock
    private lateinit var predictionsService: PredictionsService

    @Mock
    private lateinit var predictionMerger: PredictionMerger

    @InjectMocks
    private lateinit var fixturesService: FixturesService

    @Test
    fun `'update' returns list of matches when successfully updated to db`() {
        val fixturesApiResult = FixturesApiResult()
        val matches = listOf(Match())

        whenever(fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY)).thenReturn(fixturesApiResult)
        whenever(fixtureFormatter.format(fixturesApiResult)).thenReturn(matches)
        whenever(updateFixturesService.update(matches)).thenReturn(matches)

        val result = fixturesService.update()

        assertThat(result, Is(matches))
    }

    @Test
    fun `'update' returns empty list when no result from API`() {
        whenever(fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY)).thenReturn(null)

        val result = fixturesService.update()

        assertThat(result, Is(emptyList()))
    }

    @Test
    fun `'update' returns empty list when fixtures are not formatted`() {
        val fixturesApiResult = FixturesApiResult()

        whenever(fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY)).thenReturn(fixturesApiResult)
        whenever(fixtureFormatter.format(fixturesApiResult)).thenReturn(emptyList())

        val result = fixturesService.update()

        assertThat(result, Is(emptyList()))
    }

    @Test
    fun `'update' returns empty list when not successfully stored to db`() {
        val fixturesApiResult = FixturesApiResult()
        val matches = listOf(Match())

        whenever(fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY)).thenReturn(fixturesApiResult)
        whenever(fixtureFormatter.format(fixturesApiResult)).thenReturn(matches)
        whenever(updateFixturesService.update(matches)).thenReturn(emptyList())

        val result = fixturesService.update()

        assertThat(result, Is(emptyList()))
    }

    @Test
    fun `'retrieveAllMatches' should retrieve all matches`() {
        val matchEntity = MatchEntity()
        val matches = listOf(matchEntity)
        whenever(fixturesRepository.findAll()).thenReturn(matches)

        val result = fixturesService.retrieveAllMatches()

        assertThat(result.isEmpty(), Is(false))
        assertThat(result[0], Is(matchEntity.toDto()))
    }

    @Test
    fun `'retrieveAllMatches' should return empty list if no matches exist`() {
        whenever(fixturesRepository.findAll()).thenReturn(emptyList())

        val result = fixturesService.retrieveAllMatches()

        assertThat(result.isEmpty(), Is(true))
    }

    @Test
    fun `'retrieveAllPredictedMatchesByUserId' should retrieve all predicted matches by id`() {
        val matchEntity = MatchEntity()
        val matches = listOf(matchEntity)
        whenever(fixturesRepository.findPredictedMatchesByUserId(1)).thenReturn(matches)

        val result = fixturesService.retrieveAllPredictedMatchesByUserId(1)

        assertThat(result.isEmpty(), Is(false))
        assertThat(result[0], Is(matchEntity.toDto()))
    }

    @Test
    fun `'retrieveAllPredictedMatchesByUserId' should return empty list if no matches exist`() {
        whenever(fixturesRepository.findPredictedMatchesByUserId(1)).thenReturn(emptyList())

        val result = fixturesService.retrieveAllPredictedMatchesByUserId(1)

        assertThat(result.isEmpty(), Is(true))
    }

    @Test
    fun `'retrieveAllMatchesWithPredictions' should retrieve all matches with predicted scorelines by user id`() {
        val matchEntities = listOf(MatchEntity(id = 1),
                MatchEntity(id = 2))

        val matches = matchEntities.map { it.toDto() }

        val predictions = listOf(Prediction(matchId = 1, hGoals = 2, aGoals = 3),
                Prediction(matchId = 2, hGoals = 1, aGoals = 0))

        whenever(fixturesRepository.findAll()).thenReturn(matchEntities)
        whenever(predictionsService.retrievePredictionsByUserId(1)).thenReturn(predictions)
        whenever(predictionMerger.merge(matches, predictions)).thenReturn(matches)

        val result = fixturesService.retrieveAllMatchesWithPredictions(1)

        assertThat(result.isEmpty(), Is(false))
        assertThat(result[0].id, Is(1L))
        assertThat(result[1].id, Is(2L))

    }

    @Test
    fun `'retrieveAllMatchesWithPredictions' should return empty list if no matches exist`() {
        whenever(fixturesRepository.findPredictedMatchesByUserId(1)).thenReturn(emptyList())

        val result = fixturesService.retrieveAllMatchesWithPredictions(1)

        assertThat(result.isEmpty(), Is(true))
    }
}