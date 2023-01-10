package com.example.firstkotlin.service

import com.example.firstkotlin.dto.TransactionDTO
import com.example.firstkotlin.enum.TransactionStatus
import com.example.firstkotlin.exception.NoTransactionAmountException
import com.example.firstkotlin.model.Transaction
import com.example.firstkotlin.repository.mongo.UserBankRepository
import com.example.firstkotlin.repository.mongo.TransactionRepository
import com.example.firstkotlin.repository.mongo.UserBankAmountRepository
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws


@Service
class TransactionService(private val repository: TransactionRepository,
                         private val userBankRepository: UserBankRepository,
                         private val userBankAmountRepository: UserBankAmountRepository) {

    @Throws(NoTransactionAmountException::class, NotFoundException::class)
    fun saveTransaction(transactionDTO: TransactionDTO): Transaction {
        val senderUserBankId = ObjectId(transactionDTO.senderUserBankId)
        val transactionAmount = transactionDTO.transactionValue
        val userBankAmount = userBankAmountRepository.findByUserBankId(senderUserBankId)
        if (userBankAmount.accountAmount < transactionAmount) {
            throw NoTransactionAmountException(transactionAmount)
        }

        val senderUserBank = userBankRepository.findById(senderUserBankId).orElse(null)
        val receiverUserBank = userBankRepository.findById(ObjectId(transactionDTO.receiverUserBankId)).orElse(null)
        if (senderUserBank == null && receiverUserBank == null) {
            throw NotFoundException()
        }

        val transaction = Transaction(ObjectId(), senderUserBank, receiverUserBank,
            transactionAmount, TransactionStatus.ACCEPTED, Date())
        return repository.save(transaction)
    }
}