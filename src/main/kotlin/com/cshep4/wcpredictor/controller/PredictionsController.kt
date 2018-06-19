package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.data.PredictionSummary
import com.cshep4.wcpredictor.service.PredictionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/predictions")
class PredictionsController {
    @Autowired
    lateinit var predictionsService: PredictionsService

    @PostMapping("/update")
    fun updatePredictions(@RequestBody predictions: List<Prediction>) : ResponseEntity<List<Prediction>> {
        val updatedPredictions = predictionsService.savePredictions(predictions)

        return when {
            updatedPredictions.isEmpty() -> ResponseEntity.badRequest().build()
            else -> ResponseEntity.ok(updatedPredictions)
        }
    }

    @GetMapping("/user/{id}")
    fun getPredictionsByUserId(@PathVariable(value = "id") id: Long) : ResponseEntity<List<Prediction>> {
        val predictions = predictionsService.retrievePredictionsByUserId(id)

        return when {
            predictions.isEmpty() -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(predictions)
        }
    }

    @GetMapping("/summary/{id}")
    fun getPredictionsSummaryByUserId(@PathVariable(value = "id") id: Long) : ResponseEntity<PredictionSummary> {
        val predictions = predictionsService.retrievePredictionsSummaryByUserId(id)

        return ResponseEntity.ok(predictions)
    }
}