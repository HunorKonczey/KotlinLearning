package com.example.firstkotlin.datasource

import com.example.firstkotlin.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface MongoUserDataSource : MongoRepository<User, String> {

    @Query("{ 'email': ?0 }")
    fun findByEmail(email: String): User?
}