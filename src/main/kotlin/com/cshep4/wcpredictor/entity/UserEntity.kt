package com.cshep4.wcpredictor.entity

import com.cshep4.wcpredictor.data.SignUpUser
import com.cshep4.wcpredictor.data.User
import javax.persistence.*

@Entity
@Table(name = "Users")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var firstName: String= "",
    var surname: String = "",
    var email: String? = null,
    var password: String? = null
){

    fun toDto(): User = User(
            id = this.id,
            firstName = this.firstName,
            surname = this.surname,
            email = this.email,
            password = this.password)

    companion object {
        fun fromDto(dto: User) = UserEntity(
                id = dto.id!!,
                firstName = dto.firstName,
                surname = dto.surname,
                email = dto.email,
                password = dto.password)

        fun fromDto(dto: SignUpUser) = UserEntity(
                id = dto.id!!,
                firstName = dto.firstName,
                surname = dto.surname,
                email = dto.email,
                password = dto.password)
    }
}