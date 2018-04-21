package com.cshep4.wcpredictor.data

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

import java.util.Collections.emptyList

class LoginUser(val id: Long, username: String, password: String) : User(username, password, emptyList<GrantedAuthority>())
