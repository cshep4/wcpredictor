package com.cshep4.wcpedictor.repository

import com.cshep4.wcpedictor.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM User WHERE email = ?1", nativeQuery = true)
    fun findByEmail(email: String): Optional<UserEntity>

    fun findById(id: Long?): Optional<UserEntity>?

    @Query(value = "INSERT INTO User (email, password) VALUES (?1, ?2)", nativeQuery = true)
    fun save(email: String, password: String): Optional<UserEntity>?
}