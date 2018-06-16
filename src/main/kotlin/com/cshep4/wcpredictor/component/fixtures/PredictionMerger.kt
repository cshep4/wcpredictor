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

//        predictedMatches.forEach {
//            val id = it.id
//            val prediction = predictions.firstOrNull{ it.matchId == id } ?: Prediction(matchId = id, hGoals = null, aGoals = null)
//
//            it.updatePrediction(null, prediction.hGoals, prediction.aGoals)
//        }

        return predictedMatches
    }

}