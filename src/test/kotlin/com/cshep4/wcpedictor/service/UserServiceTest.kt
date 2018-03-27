package com.cshep4.wcpedictor.service

import com.cshep4.wcpedictor.data.User
import com.cshep4.wcpedictor.entity.UserEntity
import com.cshep4.wcpedictor.repository.UserRepository
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(MockitoJUnitRunner::class)
internal class UserServiceTest {
    private val email = "test@email.com"
    private val password = "test password"

    @Mock
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `'loadUserByUsername' returns User instance from db`() {
        val user = UserEntity(email = email, password = password)
        whenever(userRepository.findByEmail(email)).thenReturn(Optional.of(user))

        val result = userService.loadUserByUsername(email)!!

        assertThat(result.username, Is(email))
        assertThat(result.password, Is(password))
    }

    @Test(expected = UsernameNotFoundException::class)
    fun `'loadUserByUsername' throws UsernameNotFoundException if not user is found`() {
        whenever(userRepository.findByEmail(email)).thenReturn(Optional.empty())

        userService.loadUserByUsername(email)
    }

    @Test
    fun `'createUser' does not add user to db if password is invalid`() {
        val user = User(firstName = "first", surname = "name", email = email, password = "invalidpassword")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email!!, user.password!!)
    }

    @Test
    fun `'createUser' does not add user to db if passwords don't match`() {
        val user = User(firstName = "first", surname = "name", email = email, password = "Pass123", confirmPassword = "Word123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email!!, user.password!!)
    }

    @Test
    fun `'createUser' does not add user to db if email is invalid`() {
        val user = User(firstName = "first", surname = "name", email = "invalid email", password = "Pass123", confirmPassword = "Pass123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email!!, user.password!!)
    }

    @Test
    fun `'createUser' does not add user to db if first name is blank`() {
        val user = User(firstName = "", surname = "name", email = email, password = "Pass123", confirmPassword = "Pass123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email!!, user.password!!)
    }

    @Test
    fun `'createUser' does not add user to db if surname is blank`() {
        val user = User(firstName = "first", surname = "", email = email, password = "Pass123", confirmPassword = "Pass123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email!!, user.password!!)
    }

    @Test
    fun `'createUser' does not add user to db if user already exists with same email`() {
        val user = User(firstName = "first", surname = "name", email = email, password = "Pass123", confirmPassword = "Pass123")

        whenever(userRepository.findByEmail(email)).thenReturn(Optional.of(UserEntity.fromDto(user)))

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email!!, user.password!!)
    }

    @Test
    fun `'createUser' adds user to db`() {
        val user = User(firstName = "first", surname = "name", email = email, password = "Pass123", confirmPassword = "Pass123")

        `when`(userRepository.save(any(UserEntity::class.java))).thenReturn(UserEntity.fromDto(user))
        whenever(userRepository.findByEmail(email)).thenReturn(Optional.empty())

        val result = userService.createUser(user)

        val expectedResult = User(firstName = "first", surname = "name", email = email, password = "Pass123")

        assertThat(result, Is(expectedResult))
        verify(userRepository).save(UserEntity.fromDto(user))
    }
}