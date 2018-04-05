package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.service.FixturesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fixtures")
class FixturesController {
    @Autowired
    lateinit var fixturesService: FixturesService

    @PutMapping("/update")
    fun updateFixtures() : ResponseEntity<List<Match>> {
        val fixtures = fixturesService.update()

        return when {
            fixtures.isEmpty() -> ResponseEntity.status(BAD_REQUEST).build()
            else -> ResponseEntity.ok(fixtures)
        }
    }

    @GetMapping
    fun getAllMatches() : ResponseEntity<List<Match>> {
        val matches = fixturesService.retrieveAllMatches()

        return when {
            matches.isEmpty() -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(matches)

        }
    }
}