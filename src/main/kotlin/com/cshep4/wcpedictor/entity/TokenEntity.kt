package com.cshep4.wcpedictor.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Token")
data class TokenEntity (
    @Id
    val token: String = ""
)