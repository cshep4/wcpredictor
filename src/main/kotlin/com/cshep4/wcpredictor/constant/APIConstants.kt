package com.cshep4.wcpredictor.constant

object APIConstants {
    var API_URL: String = System.getenv("API_URL")
    var API_KEY: String = System.getenv("API_KEY")
    const val HEADER_KEY = "X-Auth-Token"
    const val USER_ID = "userId"

    var SEND_GRID_API_KEY = System.getenv("SEND_GRIP_API_KEY")
    const val RESET_PASSWORD_LINK = "https://wcpredictor.herokuapp.com/reset-password"
    const val SENDER_EMAIL = "shepapps4@gmail.com"
}