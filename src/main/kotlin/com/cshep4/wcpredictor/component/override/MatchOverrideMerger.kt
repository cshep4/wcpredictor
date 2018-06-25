package com.cshep4.wcpredictor.component.override

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.MatchWithOverride
import com.cshep4.wcpredictor.data.OverrideMatch
import org.springframework.stereotype.Component

@Component
class MatchOverrideMerger {
    fun merge(matches: List<Match>, overrides: List<OverrideMatch>) : List<MatchWithOverride> {
        return matches.map {
            val id = it.id
            val override = overrides.firstOrNull{ it.id == id } ?: OverrideMatch(id = id)

            it.toMatchWithOverride(override)
        }
    }
}