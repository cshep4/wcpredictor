package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.entity.TokenEntity
import com.cshep4.wcpredictor.repository.TokenRepository
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(MockitoJUnitRunner::class)
internal class UsedTokenServiceTest {
    private val token = "this is a token"

    @Mock
    private lateinit var tokenRepository: TokenRepository

    @InjectMocks
    private lateinit var usedTokenService: UsedTokenService

    @Test
    fun `'hasTokenBeenUsed' returns false when token has not been used`() {
        whenever(tokenRepository.getNumberOfTokens(token)).thenReturn(0)

        val result = usedTokenService.hasTokenBeenUsed(token)

        assertThat(result, Is(false))
    }

    @Test
    fun `'hasTokenBeenUsed' returns true when token has been used`() {
        whenever(tokenRepository.getNumberOfTokens(token)).thenReturn(1)

        val result = usedTokenService.hasTokenBeenUsed(token)

        assertThat(result, Is(true))
    }

    @Test
    fun `'addUsedToken' adds token to db`() {
        val tokenEntity = TokenEntity(token)
        whenever(tokenRepository.save(tokenEntity)).thenReturn(tokenEntity)

        val result = usedTokenService.addUsedToken(token)

        verify(tokenRepository).save(tokenEntity)
        assertThat(result, Is(token))
    }
}