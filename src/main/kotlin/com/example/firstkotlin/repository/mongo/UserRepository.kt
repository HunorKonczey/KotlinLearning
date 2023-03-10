package com.example.firstkotlin.repository.mongo

import com.example.firstkotlin.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface UserRepository : MongoRepository<User, ObjectId> {

    @Query("{ 'email': ?0 }")
    fun findByEmail(email: String): Optional<User>
}