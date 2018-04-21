package com.cshep4.wcpredictor.service

import com.cshep4.wcpredictor.data.SignUpUser
import com.cshep4.wcpredictor.data.User
import com.cshep4.wcpredictor.entity.UserEntity
import com.cshep4.wcpredictor.repository.UserRepository
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
        val user = SignUpUser(firstName = "first", surname = "name", email = email, password = "invalidpassword", confirmPassword = "invalidpassword")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email, user.password)
    }

    @Test
    fun `'createUser' does not add user to db if passwords don't match`() {
        val user = SignUpUser(firstName = "first", surname = "name", email = email, password = "Pass123", confirmPassword = "Word123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email, user.password)
    }

    @Test
    fun `'createUser' does not add user to db if email is invalid`() {
        val user = SignUpUser(firstName = "first", surname = "name", email = "invalid email", password = "Pass123", confirmPassword = "Pass123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email, user.password)
    }

    @Test
    fun `'createUser' does not add user to db if first name is blank`() {
        val user = SignUpUser(firstName = "", surname = "name", email = email, password = "Pass123", confirmPassword = "Pass123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email, user.password)
    }

    @Test
    fun `'createUser' does not add user to db if surname is blank`() {
        val user = SignUpUser(firstName = "first", surname = "", email = email, password = "Pass123", confirmPassword = "Pass123")

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email, user.password)
    }

    @Test
    fun `'createUser' does not add user to db if user already exists with same email`() {
        val user = SignUpUser(firstName = "first", surname = "name", email = email, password = "Pass123", confirmPassword = "Pass123")

        whenever(userRepository.findByEmail(email)).thenReturn(Optional.of(UserEntity.fromDto(user)))

        val result = userService.createUser(user)

        assertThat(result, Is(nullValue()))
        verify(userRepository, times(0)).save(user.email, user.password)
    }

    @Test
    fun `'createUser' adds user to db`() {
        val user = SignUpUser(firstName = "first", surname = "name", email = email, password = "Pass123", confirmPassword = "Pass123")

        `when`(userRepository.save(any(UserEntity::class.java))).thenReturn(UserEntity.fromDto(user))
        whenever(userRepository.findByEmail(email)).thenReturn(Optional.empty())
        whenever(bCryptPasswordEncoder.encode(user.password)).thenReturn(user.password)

        val result = userService.createUser(user)

        val expectedResult = User(firstName = "first", surname = "name", email = email, password = "Pass123")

        assertThat(result, Is(expectedResult))
        verify(userRepository).save(UserEntity.fromDto(user))
    }

    @Test
    fun `'retrieveUserById' should retrieve user if one is found with id`() {
        val user = User()

        whenever(userRepository.findById(1)).thenReturn(Optional.of(UserEntity.fromDto(user)))

        val result = userService.retrieveUserById(1)

        assertThat(result, Is(user))
    }

    @Test
    fun `'retrieveUserById' should return null if no user is found`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.empty())

        val result = userService.retrieveUserById(1)

        assertThat(result, Is(nullValue()))
    }

    @Test
    fun `'retrieveUserByEmail' should retrieve user if one is found with email`() {
        val user = User()

        whenever(userRepository.findByEmail("test")).thenReturn(Optional.of(UserEntity.fromDto(user)))

        val result = userService.retrieveUserByEmail("test")

        assertThat(result, Is(user))
    }

    @Test
    fun `'retrieveUserByEmail' should return null if no user is found`() {
        whenever(userRepository.findByEmail("test")).thenReturn(Optional.empty())

        val result = userService.retrieveUserByEmail("test")

        assertThat(result, Is(nullValue()))
    }
}