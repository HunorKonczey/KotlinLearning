package com.example.firstkotlin.dto

import com.example.firstkotlin.model.Transaction

data class TransactionResponseDTO(
    var sentTransactions: List<Transaction> = emptyList(),
    var receivedTransactions: List<Transaction> = emptyList()
)