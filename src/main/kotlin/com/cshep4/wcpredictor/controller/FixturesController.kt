package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.Match
import com.cshep4.wcpredictor.data.PredictedMatch
import com.cshep4.wcpredictor.service.FixturesService
import com.cshep4.wcpredictor.service.UserScoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/fixtures")
class FixturesController {
    @Autowired
    lateinit var fixturesService: FixturesService

    @Autowired
    lateinit var userScoreService: UserScoreService

    private fun doScoreUpdate(score: Boolean?): Boolean = score != null && score

    @PutMapping("/update")
    fun updateFixtures(@RequestParam("score") score: Boolean?) : ResponseEntity<List<Match>> {
        val fixtures = fixturesService.update()

        if (!fixtures.isEmpty() && doScoreUpdate(score)) {
            userScoreService.updateScores()
        }

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

    @GetMapping("/predicted/{id}")
    fun getAllPredictedMatches(@PathVariable(value = "id") id: Long) : ResponseEntity<List<PredictedMatch>> {
        val matches = fixturesService.retrieveAllMatchesWithPredictions(id)

        return when {
            matches.isEmpty() -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(matches)
        }
    }

    @GetMapping("/upcoming")
    fun getUpcomingFixtures() : ResponseEntity<Map<LocalDate, List<Match>>> {
        val fixtures = fixturesService.retrieveAllUpcomingFixtures()

        return ResponseEntity.ok(fixtures)
    }
}