package com.cshep4.wcpredictor.extension

import com.cshep4.wcpredictor.data.PredictedMatch

fun List<PredictedMatch>.getMatchById(id: Long?): PredictedMatch {
    return this.first { it.id == id }
}