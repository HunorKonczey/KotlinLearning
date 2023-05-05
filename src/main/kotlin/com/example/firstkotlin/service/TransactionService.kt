package com.example.firstkotlin.service

import com.example.firstkotlin.dto.TransactionDTO
import com.example.firstkotlin.dto.TransactionResponseDTO
import com.example.firstkotlin.enum.TransactionStatus
import com.example.firstkotlin.exception.NoTransactionAmountException
import com.example.firstkotlin.model.Transaction
import com.example.firstkotlin.model.User
import com.example.firstkotlin.repository.mongo.UserBankRepository
import com.example.firstkotlin.repository.mongo.TransactionRepository
import com.example.firstkotlin.repository.mongo.UserBankAmountRepository
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.jvm.Throws


@Service
class TransactionService(private val repository: TransactionRepository,
                         private val userBankRepository: UserBankRepository,
                         private val userBankAmountRepository: UserBankAmountRepository) {

    @Throws(NoTransactionAmountException::class, NotFoundException::class)
    fun saveTransaction(transactionDTO: TransactionDTO): Transaction {
        val senderUserBankId = ObjectId(transactionDTO.senderUserBankId)
        val receiverUserBankId = ObjectId(transactionDTO.receiverUserBankId)
        val transactionAmount = transactionDTO.transactionValue
        val userBankAmount = userBankAmountRepository.findByUserBankId(senderUserBankId)
        val senderUserBank = userBankRepository.findById(senderUserBankId)
            .orElseThrow { NotFoundException() }
        val receiverUserBank = userBankRepository.findById(receiverUserBankId)
            .orElseThrow { NotFoundException() }
        val transaction = Transaction(ObjectId(), senderUserBank, receiverUserBank,
            transactionAmount, TransactionStatus.DECLINED, Date())

        if (userBankAmount.accountAmount < transactionAmount) {
            repository.save(transaction)
            throw NoTransactionAmountException(transactionAmount)
        }

        transaction.transactionStatus = TransactionStatus.ACCEPTED
        userBankAmount.accountAmount = userBankAmount.accountAmount - transactionAmount
        userBankAmountRepository.save(userBankAmount)
        val receiverUserBankAmount = userBankAmountRepository.findByUserBankId(receiverUserBankId)
        receiverUserBankAmount.accountAmount = receiverUserBankAmount.accountAmount + transactionAmount
        userBankAmountRepository.save(receiverUserBankAmount)
        return repository.save(transaction)
    }

    fun getTransactions(): TransactionResponseDTO {
        val response = TransactionResponseDTO()
        val user: User = SecurityContextHolder.getContext().authentication.details as User
        val userBanks = userBankRepository.findAllByUserId(user.id!!)
        response.receivedTransactions = userBanks.stream()
            .map { userBank -> repository.findReceivedByUserBankId(userBank.id!!) }
            .flatMap { transactions -> transactions.stream() }
            .collect(Collectors.toList())
        response.sentTransactions = userBanks.stream()
            .map { userBank -> repository.findSentByUserBankId(userBank.id!!) }
            .flatMap { transactions -> transactions.stream() }
            .collect(Collectors.toList())
        return response
    }
}