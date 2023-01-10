package com.example.firstkotlin.exception

class NoTransactionAmountException(private val amount: Double) : Exception() {
    override val message: String
        get() = "Not enough amount for this transaction, transaction value: $amount"
}