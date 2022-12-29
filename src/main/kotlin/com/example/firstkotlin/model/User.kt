package com.example.firstkotlin.model

import com.example.firstkotlin.dtos.RegisterDTO
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Encrypted
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Document("users")
class User {
    @MongoId
    @JsonProperty
    private lateinit var id: String

    @JsonProperty
    private var name: String = ""

    @JsonProperty
    private var email: String = ""

    @JsonProperty("encodedPassword")
    @Encrypted
    private var password: String = ""

    object ModelMapper {
        fun from(registerDTO: RegisterDTO) = User(registerDTO.name, registerDTO.email, registerDTO.password)
    }

    constructor(name: String, email: String, password: String) {
        this.name = name
        this.email = email
        this.password = password
    }

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}