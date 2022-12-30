package com.example.firstkotlin.service

import com.example.firstkotlin.repository.MongoBankRepository
import com.example.firstkotlin.model.Bank
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class BankService(private val repository: MongoBankRepository) {
    fun getBanks(): Collection<Bank> = repository.findAll()

    fun getBank(id: String): Bank = repository.findById(ObjectId(id)).orElse(null)

    fun addBank(bank: Bank): Bank = repository.save(bank)

    fun updateBank(bank: Bank): Bank = repository.save(bank)

    fun deleteBank(id: String) = repository.deleteById(ObjectId(id))
}