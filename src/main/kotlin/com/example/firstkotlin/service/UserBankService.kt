package com.example.firstkotlin.service

import com.example.firstkotlin.model.Bank
import com.example.firstkotlin.model.User
import com.example.firstkotlin.model.UserBank
import com.example.firstkotlin.model.UserBankAmount
import com.example.firstkotlin.repository.mongo.BankRepository
import com.example.firstkotlin.repository.mongo.UserBankAmountRepository
import com.example.firstkotlin.repository.mongo.UserBankRepository
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserBankService(private val repository: UserBankRepository,
                      private val userBankAmountRepository: UserBankAmountRepository,
                      private val bankRepository: BankRepository) {

    @Throws(NotFoundException::class)
    fun saveUserBank(bankId: String) : UserBank {
        val user: User = SecurityContextHolder.getContext().authentication.details as User
        val bank: Bank = bankRepository.findById(ObjectId(bankId))
            .orElseThrow { NotFoundException() }
        val userBank = repository.save(UserBank(ObjectId(), user, bank, Date()))
        val userBankAmount = UserBankAmount(ObjectId(), userBank, 0.0, Date(), Date())
        userBankAmountRepository.save(userBankAmount)
        return userBank
    }

    fun getUserBanks() : Collection<UserBank> {
        val user: User = SecurityContextHolder.getContext().authentication.details as User
        return repository.findAllByUserId(user.id!!)
    }

    fun getUserBankById(id: String) = repository.findById(ObjectId(id))

    fun deleteById(id: String) = repository.deleteById(ObjectId(id))
}