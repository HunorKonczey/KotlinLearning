package com.example.firstkotlin.repository.interfaces

import com.example.firstkotlin.model.Bank
import org.bson.types.ObjectId

interface BankRepositoryInterface {
    fun getBanks(): Collection<Bank>

    fun getBankById(id: ObjectId): Bank

    fun addBank(bank: Bank): Bank

    fun updateBank(bank: Bank): Bank

    fun deleteBankById(id: ObjectId)
}
