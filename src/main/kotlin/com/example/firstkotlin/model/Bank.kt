package com.example.firstkotlin.model

import lombok.AllArgsConstructor

@AllArgsConstructor
data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
)