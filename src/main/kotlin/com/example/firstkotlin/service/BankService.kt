package com.example.firstkotlin.service

import com.example.firstkotlin.dto.BankDTO
import com.example.firstkotlin.repository.mongo.BankRepository
import com.example.firstkotlin.model.Bank
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.*

@Service
class BankService(private val repository: BankRepository) {
    fun getBanks(): Collection<Bank> = repository.findAll()

    fun getBank(id: String): Bank = repository.findById(ObjectId(id)).orElse(null)

    fun addBank(bankDTO: BankDTO): Bank = repository.save(Bank(ObjectId(), bankDTO.bankName, bankDTO.foundationDate))

    fun updateBank(bank: Bank): Bank = repository.save(bank)

    fun deleteBankById(id: String) = repository.deleteById(ObjectId(id))
}