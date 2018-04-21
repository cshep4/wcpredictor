package com.cshep4.wcpredictor.component.fixtures

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.PredictedMatch
import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.extension.getMatchById
import org.springframework.stereotype.Component

@Component
class PredictionMerger {
    fun merge(matches: List<Match>, predictions: List<Prediction>): List<PredictedMatch> {
        val predictedMatches = matches.map { it.toPredictedMatch() }
        predictions.forEach { predictedMatches.getMatchById(it.matchId).updatePrediction(it.id, it.hGoals, it.aGoals) }

        return predictedMatches
    }

}