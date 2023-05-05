package com.example.firstkotlin.repository.mongo

import com.example.firstkotlin.model.Transaction
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface TransactionRepository : MongoRepository<Transaction, ObjectId> {

    @Query("{ '\$or': [{ 'receiverUserBank.id': ?0 } , { 'senderUserBank.id': ?0 }]}")
    fun findByUserBankId(userBankId: ObjectId) : Collection<Transaction>

    @Query("{ 'receiverUserBank.id': ?0 }")
    fun findReceivedByUserBankId(userBankId: ObjectId) : Collection<Transaction>

    @Query("{ 'senderUserBank.id': ?0 }")
    fun findSentByUserBankId(userBankId: ObjectId) : Collection<Transaction>
}
