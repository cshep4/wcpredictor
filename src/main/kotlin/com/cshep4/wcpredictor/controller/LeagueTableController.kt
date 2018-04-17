package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.Standings
import com.cshep4.wcpredictor.service.LeagueTableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/leagueTable")
class LeagueTableController {
    @Autowired
    lateinit var leagueTableService: LeagueTableService

    @GetMapping("/current")
    fun getCurrentStandings() : ResponseEntity<Standings> {
        val standings = leagueTableService.getCurrentStandings()

        return ResponseEntity.ok(standings)
    }

    @GetMapping("/predicted/{id}")
    fun getPredictedStandings(@PathVariable(value = "id") id: Long) : ResponseEntity<Standings> {
        val standings = leagueTableService.getPredictedStandings(id)

        return ResponseEntity.ok(standings)
    }
}