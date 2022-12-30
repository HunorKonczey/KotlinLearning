package com.example.firstkotlin.repository.mock

import com.example.firstkotlin.repository.BankDataSource
import com.example.firstkotlin.model.Bank
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.NoSuchElementException

@Repository
class MockBankDataSource : BankDataSource {

    val banks = mutableListOf(
        Bank(ObjectId(), "OTP", Date(1949, 1, 1)),
        Bank(ObjectId(), "Raiffeisen", Date(1949, 1, 1)),
        Bank( ObjectId(), "IGN", Date(1949, 1, 1))
    )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBank(id: ObjectId): Bank =
        banks.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Could not find a bank with account number ${id.toHexString()}")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.id == bank.id }) {
            throw IllegalArgumentException("Bank with account number ${bank.id!!.toHexString()} already exists")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.id == bank.id }
            ?: throw NoSuchElementException("Could not find a bank with account number ${bank.id!!.toHexString()}")

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(id: ObjectId) {
        val currentBank = banks.firstOrNull { it.id == id }
                ?: throw NoSuchElementException("Could not find a bank with account number ${id.toHexString()}")

        banks.remove(currentBank)
    }
}