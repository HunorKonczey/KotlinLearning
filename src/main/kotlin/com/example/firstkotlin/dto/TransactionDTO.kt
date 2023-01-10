package com.example.firstkotlin.dto

data class TransactionDTO(
    val senderUserBankId: String,
    val receiverUserBankId: String,
    val transactionValue: Double
)