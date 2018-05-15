package com.cshep4.wcpredictor.constant

object Queries {
    const val QUERY_IS_TOKEN_USED = "SELECT used " +
            "FROM Token " +
            "WHERE token = ?1"

    const val QUERY_SET_TOKEN_TO_USED = "UPDATE token " +
            "SET used = true " +
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
            "FROM Match " +
            "INNER JOIN Prediction " +
            "ON Match.id = Prediction.matchId " +
            "WHERE Prediction.userId = ?1"

    const val QUERY_GET_SCORE_AND_RANK = "SELECT u.id, " +
            "DENSE_RANK() OVER (ORDER BY u.score DESC), " +
            "u.score " +
            "FROM Users AS u"

    const val QUERY_GET_USERS_LEAGUE_LIST = "SELECT League.name as leagueName," +
            "  League.id as pin," +
            "  (SELECT rank" +
            "    FROM (" +
            "          SELECT DENSE_RANK() OVER (ORDER BY score DESC) as rank," +
            "            id as uId" +
            "            FROM Users" +
            "            INNER JOIN UserLeague u" +
            "            ON Users.id = u.userId" +
            "            WHERE u.leagueId = League.id) as t" +
            "    WHERE uId = ?1) as rank" +
            " FROM UserLeague" +
            " INNER JOIN League" +
            " ON League.id = UserLeague.leagueId" +
            " WHERE userId = ?1"

    const val QUERY_GET_USERS_LEAGUE_OVERVIEW = "SELECT League.name as leagueName," +
            "  League.id as pin," +
            "  (SELECT rank" +
            "    FROM (" +
            "          SELECT DENSE_RANK() OVER (ORDER BY score DESC) as rank," +
            "            id as uId" +
            "            FROM Users" +
            "            INNER JOIN UserLeague u" +
            "            ON Users.id = u.userId" +
            "            WHERE u.leagueId = League.id) as t" +
            "    WHERE uId = ?2) as rank" +
            " FROM UserLeague" +
            " INNER JOIN League" +
            " ON League.id = UserLeague.leagueId" +
            " WHERE userId = ?2" +
            " AND leagueId = ?1"

    const val QUERY_GET_OVERALL_LEAGUE_OVERVIEW = "SELECT id, count, score, rank" +
            " FROM (" +
            "  SELECT" +
            "    id," +
            "    COUNT(*)" +
            "    OVER ()                 AS count," +
            "    score," +
            "    DENSE_RANK()" +
            "    OVER (" +
            "      ORDER BY score DESC ) AS rank" +
            "  FROM users" +
            " ) as u" +
            " WHERE id = ?1"

    const val QUERY_GET_LEAGUE_DETAILS = "SELECT users.id," +
            "  users.firstname," +
            "  users.surname," +
            "  users.predictedwinner," +
            "  users.score" +
            " FROM users" +
            " LEFT JOIN userleague u" +
            " ON users.id = u.userid" +
            " WHERE u.leagueid = ?1"

    const val QUERY_GET_OVERALL_LEAGUE_DETAILS = "SELECT id," +
            "  firstname," +
            "  surname," +
            "  predictedwinner," +
            "  score" +
            " FROM users"
}