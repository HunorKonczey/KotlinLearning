package com.example.firstkotlin.repository

import com.example.firstkotlin.model.Bank
import org.bson.types.ObjectId

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(id: ObjectId): Bank

    fun createBank(bank: Bank): Bank

    fun updateBank(bank: Bank): Bank

    fun deleteBank(id: ObjectId)
}
