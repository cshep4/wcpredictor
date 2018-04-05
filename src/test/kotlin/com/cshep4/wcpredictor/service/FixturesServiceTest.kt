package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.constant.APIConstants.API_KEY
import com.cshep4.wcpredictor.constant.APIConstants.API_URL
import com.cshep4.wcpredictor.constant.APIConstants.HEADER_KEY
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.api.FixturesApiResult
import com.cshep4.wcpredictor.entity.MatchEntity
import com.cshep4.wcpredictor.repository.FixturesRepository
import com.cshep4.wcpredictor.service.fixtures.FixturesApiService
import com.cshep4.wcpredictor.service.fixtures.FormatFixturesService
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
    private lateinit var formatFixturesService: FormatFixturesService

    @Mock
    private lateinit var updateFixturesService: UpdateFixturesService

    @Mock
    private lateinit var fixturesRepository: FixturesRepository

    @InjectMocks
    private lateinit var fixturesService: FixturesService

    @Test
    fun `'update' returns list of matches when successfully updated to db`() {
        val fixturesApiResult = FixturesApiResult()
        val matches = listOf(Match())

        whenever(fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY)).thenReturn(fixturesApiResult)
        whenever(formatFixturesService.format(fixturesApiResult)).thenReturn(matches)
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
        whenever(formatFixturesService.format(fixturesApiResult)).thenReturn(emptyList())

        val result = fixturesService.update()

        assertThat(result, Is(emptyList()))
    }

    @Test
    fun `'update' returns empty list when not successfully stored to db`() {
        val fixturesApiResult = FixturesApiResult()
        val matches = listOf(Match())

        whenever(fixtureApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY)).thenReturn(fixturesApiResult)
        whenever(formatFixturesService.format(fixturesApiResult)).thenReturn(matches)
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

}