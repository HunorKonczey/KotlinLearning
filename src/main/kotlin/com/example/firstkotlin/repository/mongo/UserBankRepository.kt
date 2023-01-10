package com.example.firstkotlin.repository.mongo

import com.example.firstkotlin.model.UserBank
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserBankRepository : MongoRepository<UserBank, ObjectId> {
    @Query("{ 'user.id': ?0 }")
    fun findAllByUserId(userId: ObjectId) : Collection<UserBank>
}