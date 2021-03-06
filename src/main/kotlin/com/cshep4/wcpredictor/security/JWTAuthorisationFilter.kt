package com.cshep4.wcpredictor.security

import com.cshep4.wcpredictor.constant.SecurityConstants.HEADER_STRING
import com.cshep4.wcpredictor.constant.SecurityConstants.LOGOUT_URL
import com.cshep4.wcpredictor.constant.SecurityConstants.SECRET
import com.cshep4.wcpredictor.constant.SecurityConstants.TOKEN_PREFIX
import com.cshep4.wcpredictor.extension.generateJwtToken
import com.cshep4.wcpredictor.service.UsedTokenService
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthorisationFilter(authManager: AuthenticationManager, private val usedTokenService: UsedTokenService) : BasicAuthenticationFilter(authManager) {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val header = req.getHeader(HEADER_STRING)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }

        val authentication = getAuthentication(req)

        if (authentication != null) {
            if (req.requestURI != LOGOUT_URL) {
                res.generateJwtToken(authentication.principal.toString(), req.requestURI)
            }
        }

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING) ?: return null

        val user = Jwts.parser()
                .setSigningKey(SECRET.toByteArray())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .body
                .issuer

        return when {
            user != null -> UsernamePasswordAuthenticationToken(user, null, ArrayList<GrantedAuthority>())
            else -> null
        }
    }
}