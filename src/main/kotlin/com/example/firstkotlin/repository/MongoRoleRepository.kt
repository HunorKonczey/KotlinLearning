package com.example.firstkotlin.repository

import com.example.firstkotlin.model.Role
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface MongoRoleRepository : MongoRepository<Role, ObjectId> {

    @Query("{ 'name': ?0 }")
    fun findByName(name: String): Optional<Role>
}