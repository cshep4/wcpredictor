package com.cshep4.wcpedictor.extension

import com.cshep4.wcpedictor.constant.SecurityConstants
import com.cshep4.wcpedictor.constant.SecurityConstants.HEADER_STRING
import com.cshep4.wcpedictor.constant.SecurityConstants.TOKEN_PREFIX
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.generateJwtToken(user: String) {
    val token = Jwts.builder()
            .setSubject(user)
            .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.toByteArray())
            .compact()

    System.out.println("New JWT Generated: $token")
    this.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
}