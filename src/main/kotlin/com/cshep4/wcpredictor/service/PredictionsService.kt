package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.entity.PredictionEntity
import com.cshep4.wcpredictor.repository.PredictionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PredictionsService {
    @Autowired
    private lateinit var predictionsRepository: PredictionsRepository

    fun savePredictions(predictions: List<Prediction>) : List<Prediction> {
        val predictionEntities = predictions.map { PredictionEntity.fromDto(it) }

        return predictionsRepository.saveAll(predictionEntities).map { it.toDto() }
    }

    fun retrievePredictionsByUserId(id: Long) : List<Prediction> = predictionsRepository.findByUserId(id).map { it.toDto() }
}