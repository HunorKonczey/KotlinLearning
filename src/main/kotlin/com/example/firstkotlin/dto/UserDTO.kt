package com.example.firstkotlin.dto

import com.example.firstkotlin.enum.RoleType

data class UserDTO(val email: String,
                   val roles: Collection<RoleType>,
                   val refreshToken: String,
                   val accessToken: String)