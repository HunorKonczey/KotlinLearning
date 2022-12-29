package com.example.firstkotlin.service

import com.example.firstkotlin.datasource.MongoBankDataSource
import com.example.firstkotlin.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: MongoBankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.findAll()

    fun getBank(accountNumber: String): Bank = dataSource.findById(accountNumber).get()

    fun addBank(bank: Bank): Bank = dataSource.save(bank)

    fun updateBank(bank: Bank): Bank = dataSource.save(bank)

    fun deleteBank(accountNumber: String) = dataSource.deleteById(accountNumber)
}