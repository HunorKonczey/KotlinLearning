package com.example.firstkotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonGetter
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.Date

@Document("banks")
open class Bank(
    @MongoId
    var id: ObjectId? = ObjectId(),
    var name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var foundationDate: Date
) {
    @JsonGetter("id")
    fun getId(): String = id!!.toHexString()
}