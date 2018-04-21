package com.cshep4.wcpredictor.service.fixtures

import com.cshep4.wcpredictor.constant.APIConstants.API_KEY
import com.cshep4.wcpredictor.constant.APIConstants.API_URL
import com.cshep4.wcpredictor.constant.APIConstants.HEADER_KEY
import com.cshep4.wcpredictor.data.api.FixturesApiResult
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

internal class FixturesApiServiceTest {
    private val fixturesApiService = FixturesApiService()

    @Test
    fun `'retrieveFixtures' parses API result and returns object`() {
        val fixturesApiResult = fixturesApiService.retrieveFixtures(API_URL, HEADER_KEY, API_KEY)

        assertThat(fixturesApiResult, instanceOf(FixturesApiResult::class.java))
        assertThat(fixturesApiResult, Is(notNullValue()))
    }
}