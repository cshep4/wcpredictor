package com.cshep4.wcpredictor.controller

import com.cshep4.wcpredictor.data.SignUpUser
import com.cshep4.wcpredictor.data.User
import com.cshep4.wcpredictor.service.UserService
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus.*
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(MockitoJUnitRunner::class)
internal class UserControllerTest {
    @Mock
    lateinit var userService: UserService

    @InjectMocks
    lateinit var userController: UserController

    val userInput = SignUpUser(firstName = "first", surname = "surname", email = "email", password = "pass", confirmPassword = "pass", predictedWinner = "France")
    val user = User()

    @Test
    fun `'signUp' returns CREATED with the user in the request body when user is added to db`() {
        whenever(userService.createUser(userInput)).thenReturn(user)

        val result = userController.signUp(userInput)

        assertThat(result.statusCode, Is(CREATED))
        assertThat(result.body, Is(user))
    }

    @Test
    fun `'signUp' returns BAD_REQUEST user is not added to db`() {
        whenever(userService.createUser(userInput)).thenReturn(null)

        val result = userController.signUp(userInput)

        assertThat(result.statusCode, Is(BAD_REQUEST))
        assertThat(result.body, Is(nullValue()))
    }

    @Test
    fun `'getUserInfo' returns user info in the request body when user is found with id`() {
        whenever(userService.retrieveUserById(1)).thenReturn(user)

        val result = userController.getUserInfo(1)

        assertThat(result.statusCode, Is(OK))
        assertThat(result.body, Is(user))
    }

    @Test
    fun `'getUserInfo' returns NOT_FOUND when no user is found`() {
        whenever(userService.retrieveUserById(1)).thenReturn(null)

        val result = userController.getUserInfo(1)

        assertThat(result.statusCode, Is(NOT_FOUND))
        assertThat(result.body, Is(nullValue()))
    }

    @Test
    fun `'getUserByEmail' returns user info in the request body when user is found with email`() {
        whenever(userService.retrieveUserByEmail("test")).thenReturn(user)

        val result = userController.getUserByEmail("test")

        assertThat(result.statusCode, Is(OK))
        assertThat(result.body, Is(user))
    }

    @Test
    fun `'getUserByEmail' returns NOT_FOUND when no user is found`() {
        whenever(userService.retrieveUserByEmail("test")).thenReturn(null)

        val result = userController.getUserByEmail("test")

        assertThat(result.statusCode, Is(NOT_FOUND))
        assertThat(result.body, Is(nullValue()))
    }

    @Test
    fun `'logout' returns OK`() {
        val result = userController.logout()

        assertThat(result.statusCode, Is(OK))
        assertThat(result.body, Is(nullValue()))
    }
}