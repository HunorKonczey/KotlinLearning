package com.example.firstkotlin.model

import com.example.firstkotlin.enum.RoleType
import com.fasterxml.jackson.annotation.JsonGetter
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document("roles")
data class Role(
    @MongoId
    var id: ObjectId? = ObjectId(),
    val name: RoleType
) {
    @JsonGetter("id")
    fun getId(): String = id!!.toHexString()
}