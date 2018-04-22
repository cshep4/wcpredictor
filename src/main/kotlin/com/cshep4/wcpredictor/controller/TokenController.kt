package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.service.UsedTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token")
class TokenController {
    @Autowired
    lateinit var usedTokenService: UsedTokenService

    @PutMapping("/used")
    fun setUsedToken(@RequestBody token: String) : ResponseEntity<String> {
        val result = usedTokenService.setUsedToken(token)

        return when {
            result > 0 -> ResponseEntity.ok().build()
            else -> ResponseEntity.badRequest().build()
        }
    }
}