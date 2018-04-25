package com.cshep4.wcpredictor.service


import com.cshep4.wcpredictor.data.LoginUser
import com.cshep4.wcpredictor.data.SignUpUser
import com.cshep4.wcpredictor.entity.UserEntity
import com.cshep4.wcpredictor.extension.isValidEmailAddress
import com.cshep4.wcpredictor.extension.isValidPassword
import com.cshep4.wcpredictor.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService : UserDetailsService {
    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails? {
        val user = userRepository.findByEmail(email)
                .map { it.toDto() }
                .orElse(null) ?: throw UsernameNotFoundException("User not found")

        return LoginUser(user.id!!, user.email!!, user.password!!)
    }

    fun createUser(signUpUser: SignUpUser): com.cshep4.wcpredictor.data.User? {
        signUpUser.score = 0

        return when {
            !signUpUser.email.isValidEmailAddress() -> null
            userRepository.findByEmail(signUpUser.email).isPresent -> null
            !signUpUser.password.isValidPassword() -> null
            signUpUser.firstName.isBlank() -> null
            signUpUser.surname.isBlank() -> null
            signUpUser.password != signUpUser.confirmPassword -> null
            else -> {
                signUpUser.password = bCryptPasswordEncoder.encode(signUpUser.password)
                userRepository.save(UserEntity.fromDto(signUpUser)).toDto()
            }
        }
    }

    fun retrieveUserById(id: Long): com.cshep4.wcpredictor.data.User? = userRepository.findById(id)
            .map { it.toDto() }
            .orElse(null)

    fun retrieveUserByEmail(email: String): com.cshep4.wcpredictor.data.User? = userRepository.findByEmail(email)
            .map { it.toDto() }
            .orElse(null)
}