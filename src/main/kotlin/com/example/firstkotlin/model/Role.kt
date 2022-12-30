package com.example.firstkotlin.model

import com.example.firstkotlin.enum.RoleType
import com.fasterxml.jackson.annotation.JsonGetter
import lombok.AllArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@AllArgsConstructor
@Document("roles")
data class Role(
    @MongoId
    val id: ObjectId? = ObjectId(),
    val name: RoleType
) {
    @JsonGetter("id")
    fun getId(): String = id!!.toHexString()
}