package com.cshep4.wcpredictor.constant

object APIConstants {
    var API_URL: String = System.getenv("API_URL")
    var API_KEY: String = System.getenv("API_KEY")
    const val HEADER_KEY = "X-Auth-Token"
    const val USER_ID = "userId"
}