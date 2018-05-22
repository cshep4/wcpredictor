package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.data.Prediction
import com.cshep4.wcpredictor.entity.PredictionEntity
import com.cshep4.wcpredictor.repository.PredictionsRepository
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class PredictionsServiceTest {
    @Mock
    private lateinit var predictionsRepository: PredictionsRepository

    @InjectMocks
    private lateinit var predictionsService: PredictionsService

    @Test
    fun `'update' returns list of predictions when successfully updated to db`() {
        val predictions = listOf(Prediction(aGoals = 0, hGoals = 0))
        val predictionEntities = predictions.map { PredictionEntity.fromDto(it) }

        whenever(predictionsRepository.saveAll(predictionEntities)).thenReturn(predictionEntities)

        val result = predictionsService.savePredictions(predictions)

        assertThat(result, `is`(predictions))
    }

    @Test
    fun `'update' returns empty list when not successfully stored to db`() {
        val predictions = listOf(Prediction())
        val predictionEntities = predictions.map { PredictionEntity.fromDto(it) }

        whenever(predictionsRepository.saveAll(predictionEntities)).thenReturn(emptyList())

        val result = predictionsService.savePredictions(predictions)

        assertThat(result, `is`(emptyList()))
    }

    @Test
    fun `'retrievePredictionsByUserId' should retrieve all predictions for that user`() {
        val predictionEntity = PredictionEntity()
        val predictions = listOf(predictionEntity)
        whenever(predictionsRepository.findByUserId(1)).thenReturn(predictions)

        val result = predictionsService.retrievePredictionsByUserId(1)

        assertThat(result.isEmpty(), `is`(false))
        assertThat(result[0], `is`(predictionEntity.toDto()))
    }

    @Test
    fun `'retrievePredictionsByUserId' should return empty list if no predictions exist for that user id`() {
        whenever(predictionsRepository.findByUserId(1)).thenReturn(emptyList())

        val result = predictionsService.retrievePredictionsByUserId(1)

        assertThat(result.isEmpty(), `is`(true))
    }
}