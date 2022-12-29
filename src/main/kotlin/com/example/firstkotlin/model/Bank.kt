package com.example.firstkotlin.model

import lombok.AllArgsConstructor
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@AllArgsConstructor
@Document("banks")
data class Bank(
    @MongoId
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
)