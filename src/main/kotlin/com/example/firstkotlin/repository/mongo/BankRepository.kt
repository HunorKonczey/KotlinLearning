package com.example.firstkotlin.repository.mongo

import com.example.firstkotlin.model.Bank
import com.example.firstkotlin.repository.interfaces.BankRepositoryInterface
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface BankRepository : MongoRepository<Bank, ObjectId>