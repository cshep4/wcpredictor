package com.cshep4.wcpredictor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class WcPredictorApplication

fun main(args: Array<String>) {
    runApplication<WcPredictorApplication>(*args)
}