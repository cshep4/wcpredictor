package com.cshep4.wcpredictor.extension

import com.cshep4.wcpredictor.data.LeagueTable
import com.cshep4.wcpredictor.data.TableTeam

fun MutableList<LeagueTable>.groupDoesNotExist(group: Char) : Boolean {
    return this.none { it.group == group }
}

fun MutableList<LeagueTable>.getGroupTable(group: Char) : MutableList<TableTeam> {
    return this.first { it.group == group }.table
}