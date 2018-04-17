package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.data.api.Fixture
import com.cshep4.wcpredictor.data.api.FixturesApiResult
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.hamcrest.CoreMatchers.`is` as Is

internal class FixtureFormatterTest {
    private val fixtureFormatter = FixtureFormatter()

    @Test
    fun `'format' returns list of matches`() {
        val match = Fixture(homeTeamName = "Russia", date = "2000-01-01T12:00:00Z")

        val apiResult = FixturesApiResult(fixtures = arrayOf(match,match,match,match))

        val result = fixtureFormatter.format(apiResult)

        val expectedDateTime = LocalDateTime.parse(match.date, DateTimeFormatter.ISO_DATE_TIME)

        assertThat(result.size, Is(4))
        assertThat(result[0].id, Is(1L))
        assertThat(result[0].hTeam, Is(match.homeTeamName))
        assertThat(result[0].aTeam, Is(match.awayTeamName))
        assertThat(result[0].hGoals, Is(match.result?.goalsHomeTeam))
        assertThat(result[0].aGoals, Is(match.result?.goalsAwayTeam))
        assertThat(result[0].played, Is(0))
        assertThat(result[0].group, Is('A'))
        assertThat(result[0].dateTime, Is(expectedDateTime))
        assertThat(result[0].matchday, Is(match.matchday))
        assertThat(result[1].id, Is(2L))
        assertThat(result[2].id, Is(3L))
        assertThat(result[3].id, Is(4L))
    }

    @Test
    fun `'format' returns empty list if no fixtures`() {
        val apiResult = FixturesApiResult(fixtures = emptyArray())

        val result = fixtureFormatter.format(apiResult)

        assertThat(result.isEmpty(), Is(true))
    }
}