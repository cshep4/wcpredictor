package com.cshep4.wcpedictor.data

data class User (val id: Long? = 0, var firstName: String = "", var surname: String = "", var email: String? = null, var password: String? = null, var confirmPassword: String? = null)