package com.cshep4.wcpredictor.extension

import com.cshep4.wcpredictor.data.Match

fun List<Match>.getMatchById(id: Long?): Match {
    return this.first { it.id == id }
}