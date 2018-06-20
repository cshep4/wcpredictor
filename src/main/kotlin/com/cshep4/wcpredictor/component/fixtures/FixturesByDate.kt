package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.data.Match
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class FixturesByDate {
    fun format(matches: List<Match>): Map<LocalDate, List<Match>> = matches
            .sortedBy { it.dateTime }
            .groupBy { it.dateTime!!.toLocalDate() }
}