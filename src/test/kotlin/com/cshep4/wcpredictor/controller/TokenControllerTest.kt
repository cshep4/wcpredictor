package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.service.UsedTokenService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus.OK

@RunWith(MockitoJUnitRunner::class)
internal class TokenControllerTest {
    companion object {
        const val TOKEN = "token"
    }

    @Mock
    private lateinit var usedTokenService: UsedTokenService

    @InjectMocks
    private lateinit var tokenController: TokenController

    @Test
    fun `'setUsedToken' returns OK when token is set to be used`() {
//        whenever(usedTokenService.setUsedToken(TOKEN)).thenReturn(1)

        val result = tokenController.setUsedToken(TOKEN)

        assertThat(result.statusCode, `is`(OK))
    }

//    @Test
//    fun `'setUsedToken' returns BAD REQUEST when token is invalid`() {
//        whenever(usedTokenService.setUsedToken(TOKEN)).thenReturn(0)
//
//        val result = tokenController.setUsedToken(TOKEN)
//
//        assertThat(result.statusCode, `is`(BAD_REQUEST))
//    }
}