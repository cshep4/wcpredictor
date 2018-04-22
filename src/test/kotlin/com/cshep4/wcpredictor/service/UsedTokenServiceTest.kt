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
    companion object {
        const val TOKEN = "this is a token"
        const val NUMBER_TOKENS_MODIFIED = 32
    }

    @Mock
    private lateinit var tokenRepository: TokenRepository

    @InjectMocks
    private lateinit var usedTokenService: UsedTokenService

    @Test
    fun `'hasTokenBeenUsed' returns false when token has not been used`() {
        whenever(tokenRepository.isTokenUsed(TOKEN)).thenReturn(false)

        val result = usedTokenService.hasTokenBeenUsed(TOKEN)

        assertThat(result, Is(false))
    }

    @Test
    fun `'hasTokenBeenUsed' returns true when token has been used`() {
        whenever(tokenRepository.isTokenUsed(TOKEN)).thenReturn(true)

        val result = usedTokenService.hasTokenBeenUsed(TOKEN)

        assertThat(result, Is(true))
    }

    @Test
    fun `'addUsedToken' adds token to db`() {
        val tokenEntity = TokenEntity(TOKEN)
        whenever(tokenRepository.save(tokenEntity)).thenReturn(tokenEntity)

        val result = usedTokenService.addUsedToken(TOKEN)

        verify(tokenRepository).save(tokenEntity)
        assertThat(result, Is(TOKEN))
    }

    @Test
    fun `'setUsedToken' returns number of tokens modified when token set to used`() {
        whenever(tokenRepository.setTokenToUsed(TOKEN)).thenReturn(NUMBER_TOKENS_MODIFIED)

        val result = usedTokenService.setUsedToken(TOKEN)

        verify(tokenRepository).setTokenToUsed(TOKEN)
        assertThat(result, Is(NUMBER_TOKENS_MODIFIED))
    }
}