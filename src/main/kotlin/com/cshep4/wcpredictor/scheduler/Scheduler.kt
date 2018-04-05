package com.cshep4.wcpredictor.scheduler

import com.cshep4.wcpredictor.service.FixturesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class Scheduler {
    @Autowired
    lateinit var fixturesService: FixturesService

    @Scheduled(fixedRate = 36000)
    fun scheduleTaskWithFixedRate() {
//        fixturesService.update()
    }
}