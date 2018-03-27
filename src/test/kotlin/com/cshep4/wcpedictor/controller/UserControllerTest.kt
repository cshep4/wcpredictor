package com.cshep4.wcpedictor.controller

import com.cshep4.wcpedictor.data.User
import com.cshep4.wcpedictor.service.UserService
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(MockitoJUnitRunner::class)
internal class UserControllerTest {
    @Mock
    lateinit var userService: UserService

    @InjectMocks
    lateinit var userController: UserController

    val user = User()

    @Test
    fun `'signUp' returns CREATED with the user in the request body when user is added to db`() {
        whenever(userService.createUser(user)).thenReturn(user)

        val result = userController.signUp(user)

        assertThat(result.statusCode, Is(CREATED))
        assertThat(result.body, Is(user))
    }

    @Test
    fun `'signUp' returns BAD_REQUEST user is not added to db`() {
        whenever(userService.createUser(user)).thenReturn(null)

        val result = userController.signUp(user)

        assertThat(result.statusCode, Is(BAD_REQUEST))
        assertThat(result.body, Is(nullValue()))
    }
}