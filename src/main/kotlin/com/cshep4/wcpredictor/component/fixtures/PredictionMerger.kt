package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.extension.getMatchById
import org.springframework.stereotype.Component

@Component
class PredictionMerger {
    fun merge(matches: List<Match>, predictions: List<Prediction>): List<Match> {
        predictions.forEach { matches.getMatchById(it.matchId).updateScoreline(it.hGoals, it.aGoals) }

        return matches
    }

}