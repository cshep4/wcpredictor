package com.cshep4.wcpredictor.component.prediction

import com.cshep4.wcpredictor.component.fixtures.GroupFixturesCollector
import com.cshep4.wcpredictor.component.fixtures.KnockoutFixturesCollector
import com.cshep4.wcpredictor.component.fixtures.PredictionMerger
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.data.PredictionSummary
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreatePredictionSummary {
    @Autowired
    private lateinit var predictionMerger: PredictionMerger

    @Autowired
    private lateinit var groupFixturesCollector: GroupFixturesCollector

    @Autowired
    private lateinit var knockoutFixturesCollector: KnockoutFixturesCollector

    fun format(matches: List<Match>, predictions: List<Prediction>) : PredictionSummary {
        val matchesWithPredictions = predictionMerger.merge(matches, predictions).map { it.toMatch() }

        val groups = groupFixturesCollector.collect(matchesWithPredictions)
        val knockout = knockoutFixturesCollector.collect(matchesWithPredictions)

        return PredictionSummary(group = groups, knockout = knockout)
    }
}