package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.MatchWithOverride
import com.cshep4.wcpredictor.data.OverrideMatch
import com.cshep4.wcpredictor.service.OverrideMatchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/override")
class OverrideMatchController {
    @Autowired
    lateinit var overrideMatchService: OverrideMatchService

    @PutMapping("/update")
    fun updateOverrides(@RequestBody overrides: List<OverrideMatch>) : ResponseEntity<List<OverrideMatch>> {
        val result = overrideMatchService.updateOverrides(overrides)

        return ResponseEntity.ok(result)
    }

    @GetMapping
    fun getAllOverriddenMatches() : ResponseEntity<List<MatchWithOverride>> {
        val matches = overrideMatchService.retrieveAllMatchesWithOverrideScores()

        return ResponseEntity.ok(matches)
    }
}