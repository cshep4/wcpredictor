package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.api.FixturesApiResult
import org.springframework.stereotype.Component

@Component
class FixtureFormatter {
    fun format(fixturesApiResult: FixturesApiResult): List<Match> {
        var i :Long = 1

        return fixturesApiResult.fixtures
                ?.map { it.toMatch(i++) }
                .orEmpty()
    }

}