package com.cshep4.wcpredictor.component.prediction

import com.cshep4.wcpredictor.component.fixtures.GroupFixturesCollector
import com.cshep4.wcpredictor.component.fixtures.KnockoutFixturesCollector
import com.cshep4.wcpredictor.component.fixtures.PredictionMerger
import com.cshep4.wcpredictor.data.GroupPredictions
import com.cshep4.wcpredictor.data.KnockoutStandings
import com.cshep4.wcpredictor.data.PredictedMatch
import com.cshep4.wcpredictor.data.Prediction
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class CreatePredictionSummaryTest {
    @Mock
    private lateinit var predictionMerger: PredictionMerger

    @Mock
    private lateinit var groupFixturesCollector: GroupFixturesCollector

    @Mock
    private lateinit var knockoutFixturesCollector: KnockoutFixturesCollector

    @InjectMocks
    private lateinit var createPredictionSummary: CreatePredictionSummary

    @Test
    fun `'format' collects both sets of matches and returns in the right format`() {
        val predictedMatches = listOf(PredictedMatch())
        val matches = predictedMatches.map { it.toMatch() }
        val predictions = listOf(Prediction())
        val groupPredictions = listOf(GroupPredictions())
        val knockoutStandings = listOf(KnockoutStandings())

        whenever(predictionMerger.merge(matches, predictions)).thenReturn(predictedMatches)
        whenever(groupFixturesCollector.collect(matches)).thenReturn(groupPredictions)
        whenever(knockoutFixturesCollector.collect(matches)).thenReturn(knockoutStandings)

        val result = createPredictionSummary.format(matches, predictions)

        assertThat(result.group, `is`(groupPredictions))
        assertThat(result.knockout, `is`(knockoutStandings))
    }

}