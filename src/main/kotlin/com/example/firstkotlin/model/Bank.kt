package com.example.firstkotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonGetter
import lombok.AllArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.Date

@AllArgsConstructor
@Document("banks")
data class Bank(
    @MongoId
    val id: ObjectId? = ObjectId(),
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val foundationDate: Date
) {
    @JsonGetter("id")
    fun getId(): String = id!!.toHexString()
}