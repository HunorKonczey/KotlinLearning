package com.example.firstkotlin.repository

import com.example.firstkotlin.model.Bank
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoBankRepository : MongoRepository<Bank, ObjectId>