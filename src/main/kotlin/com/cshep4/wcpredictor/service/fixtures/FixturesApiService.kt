package com.cshep4.wcpredictor.service.fixtures

import com.cshep4.wcpredictor.data.api.FixturesApiResult
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.httpGet
import org.springframework.stereotype.Service

@Service
class FixturesApiService {
    fun retrieveFixtures(url: String, headerKey: String, headerValue: String): FixturesApiResult? {
        val (req, res, result) = url.httpGet(listOf(headerKey to headerValue)).responseString()

        return result.fold({ data ->
            return@fold ObjectMapper().readValue(data, FixturesApiResult::class.java)
        }, { err ->
            return@fold null
        })
    }

}