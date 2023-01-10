package com.example.firstkotlin.model

import com.example.firstkotlin.dto.RegisterDTO
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Encrypted
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@Document("users")
open class User(
    @MongoId
    var id: ObjectId? = ObjectId(),
    val name: String,
    @Indexed(unique = true)
    val email: String,
    @Encrypted
    @JsonIgnore
    val password: String,
    @DBRef(lazy = true)
    val roleIds: MutableList<Role>,
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @CreatedDate
//    val createdDate: Date,
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @LastModifiedDate
//    val updatedAt: Date
){

    @JsonGetter("id")
    fun getId(): String = id!!.toHexString()

    object ModelMapper {
        fun from(registerDTO: RegisterDTO) = User(
            null,
            registerDTO.name,
            registerDTO.email,
            BCryptPasswordEncoder().encode(registerDTO.password),
            mutableListOf(),
//            Date(),
//            Date()
        )
    }
}