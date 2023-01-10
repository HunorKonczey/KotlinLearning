package com.example.firstkotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*

@Document("user_banks")
@CompoundIndex(name = "user_bank_idx", def = "{'user.id': 1, 'bank.id': 1}", unique = true)
open class UserBank(
    @MongoId
    var id: ObjectId? = ObjectId(),
    @DBRef(lazy = false)
    val user: User,
    @DBRef(lazy = false)
    val bank: Bank,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    val addedDate: Date
)