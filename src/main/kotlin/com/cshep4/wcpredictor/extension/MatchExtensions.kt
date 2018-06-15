package com.cshep4.wcpredictor.extension

import com.cshep4.wcpredictor.data.PredictedMatch

fun List<PredictedMatch>.getMatchById(id: Long?): PredictedMatch {
    return this.firstOrNull{ it.id == id } ?: PredictedMatch(id = id!!, aGoals = null, hGoals = null)
}