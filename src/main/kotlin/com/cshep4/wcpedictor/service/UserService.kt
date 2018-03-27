package com.cshep4.wcpedictor.service


import com.cshep4.wcpedictor.entity.UserEntity
import com.cshep4.wcpedictor.extension.isValidEmailAddress
import com.cshep4.wcpedictor.extension.isValidPassword
import com.cshep4.wcpedictor.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
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
        val user = userRepository.findByEmail(email).map { it.toDto() }.orElse(null) ?: throw UsernameNotFoundException("User not found")

        return User(user.email, user.password, emptyList())
    }

    fun createUser(user: com.cshep4.wcpedictor.data.User): com.cshep4.wcpedictor.data.User? {
        return when {
            !user.email.isValidEmailAddress() -> null
            userRepository.findByEmail(user.email!!).isPresent -> null
            !user.password.isValidPassword() -> null
            user.firstName.isBlank() -> null
            user.surname.isBlank() -> null
            user.password != user.confirmPassword -> null
            else -> {
                user.password = bCryptPasswordEncoder.encode(user.password)
                userRepository.save(UserEntity.fromDto(user)).toDto()
            }
        }
    }
}