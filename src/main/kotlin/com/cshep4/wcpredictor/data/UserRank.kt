package com.cshep4.wcpredictor.data

import org.springframework.beans.factory.annotation.Value

data class UserRank(
        @Value(value = "#{target.id}")
        val id: Long = 0,
        @Value(value = "#{target.rank}")
        val rank: Long = 0,
        @Value(value = "#{target.score}")
        val score: Int = 0
)