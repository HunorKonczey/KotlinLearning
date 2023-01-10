package com.example.firstkotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*

@Document("user_bank_amounts")
open class UserBankAmount(
    @MongoId
    var id: ObjectId? = ObjectId(),
    @DBRef(lazy = false)
    @Indexed(unique = true)
    val userBank: UserBank,
    val accountAmount: Double,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    val updatedDate: Date,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    val addedDate: Date
)