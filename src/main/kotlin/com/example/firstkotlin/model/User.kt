package com.example.firstkotlin.model

import com.example.firstkotlin.dto.RegisterDTO
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Encrypted
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Document("users")
@NoArgsConstructor
data class User(
    @MongoId
    val id: ObjectId? = ObjectId(),
    val name: String,
    val email: String,
    @Encrypted
    @JsonIgnore
    val password: String,
    @DBRef(lazy = true)
    val roleIds: MutableList<Role>) {

    @JsonGetter("id")
    fun getId(): String = id!!.toHexString()

    object ModelMapper {
        fun from(registerDTO: RegisterDTO) = User(
            null,
            registerDTO.name,
            registerDTO.email,
            BCryptPasswordEncoder().encode(registerDTO.password),
            mutableListOf()
        )
    }
}