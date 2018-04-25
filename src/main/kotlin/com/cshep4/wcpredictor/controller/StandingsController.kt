package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.StandingsOverview
import com.cshep4.wcpredictor.service.StandingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/standings")
class StandingsController {
    @Autowired
    lateinit var standingsService: StandingsService

    @GetMapping("/userLeagues/{id}")
    fun getUsersLeagueList(@PathVariable(value = "id") id: Long) : ResponseEntity<StandingsOverview> {
        val standingsOverview = standingsService.retrieveStandingsOverview(id)

        return ResponseEntity.ok(standingsOverview)
    }
}