package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.component.prediction.CreatePredictionSummary
import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.data.PredictionSummary
import com.cshep4.wcpredictor.entity.PredictionEntity
import com.cshep4.wcpredictor.repository.PredictionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDateTime

@Service
class PredictionsService {
    @Autowired
    private lateinit var predictionsRepository: PredictionsRepository

    @Autowired
    private lateinit var fixturesService: FixturesService

    @Autowired
    private lateinit var createPredictionSummary: CreatePredictionSummary

    private lateinit var matches: List<Match>

    fun savePredictions(predictions: List<Prediction>) : List<Prediction> {
        matches = fixturesService.retrieveAllMatches()

        val predictionEntities = predictions
                .filter { matchYetToPlay(it.matchId!!) }
                .map { PredictionEntity.fromDto(it) }

        return predictionsRepository.saveAll(predictionEntities).map { it.toDto() }
    }

    private fun matchYetToPlay(id: Long) : Boolean {
        return matches.first { it.id == id }
                .dateTime!!
                .isAfter(LocalDateTime.now(Clock.systemUTC()))
    }

    fun retrievePredictionsByUserId(id: Long) : List<Prediction> = predictionsRepository.findByUserId(id).map { it.toDto() }

    fun retrievePredictionsSummaryByUserId(id: Long) : PredictionSummary {
        val matches = fixturesService.retrieveAllMatches().filter { it.dateTime!!.isBefore(LocalDateTime.now(Clock.systemUTC())) }
        val predictions = retrievePredictionsByUserId(id)

        return createPredictionSummary.format(matches, predictions)
    }
}