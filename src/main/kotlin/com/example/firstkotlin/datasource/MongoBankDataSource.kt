package com.example.firstkotlin.datasource

import com.example.firstkotlin.model.Bank
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoBankDataSource : MongoRepository<Bank, String>