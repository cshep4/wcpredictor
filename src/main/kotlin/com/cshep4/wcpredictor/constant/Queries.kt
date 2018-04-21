package com.cshep4.wcpredictor.constant

object Queries {
    const val QUERY_IS_TOKEN_USED = "SELECT COUNT(*) " +
            "FROM Token " +
            "WHERE token = ?1"

    const val QUERY_GET_USER_BY_EMAIL = "SELECT * " +
            "FROM Users " +
            "WHERE email = ?1"
    const val QUERY_SAVE_USER = "INSERT INTO Users (email, password) " +
            "VALUES (?1, ?2)"

    const val QUERY_GET_PREDICTIONS_BY_USER_ID = "SELECT * " +
            "FROM Prediction " +
            "WHERE userId = ?1"
    const val QUERY_GET_PREDICTED_MATCHES_BY_USER_ID = "SELECT Match.id, " +
            "Match.hTeam, " +
            "Match.aTeam, " +
            "Prediction.hGoals, " +
            "Prediction.aGoals, " +
            "Match.played, " +
            "Match.matchGroup, " +
            "Match.dateTime, " +
            "Match.matchday " +
            "Match.matchday " +
            "FROM Match " +
            "INNER JOIN Prediction " +
            "ON Match.id = Prediction.matchId " +
            "WHERE Prediction.userId = ?1"
}