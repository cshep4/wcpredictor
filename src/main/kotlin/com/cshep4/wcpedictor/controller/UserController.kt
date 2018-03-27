package com.cshep4.wcpedictor.controller

import com.cshep4.wcpedictor.data.User
import com.cshep4.wcpedictor.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @PostMapping("/sign-up")
    fun signUp(@RequestBody user: User) : ResponseEntity<User> {
        val savedUser = userService.createUser(user)

        return when (savedUser) {
            null -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            else -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser)
        }
    }
}