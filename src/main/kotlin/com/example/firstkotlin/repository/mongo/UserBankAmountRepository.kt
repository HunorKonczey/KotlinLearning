package com.example.firstkotlin.repository.mongo

import com.example.firstkotlin.model.UserBankAmount
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserBankAmountRepository : MongoRepository<UserBankAmount, ObjectId> {

    @Query("{ 'userBank.id': ?0 }")
    fun findByUserBankId(userBankId: ObjectId) : UserBankAmount
}
