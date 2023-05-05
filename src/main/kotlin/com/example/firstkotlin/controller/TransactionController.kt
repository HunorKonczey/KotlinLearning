package com.example.firstkotlin.controller

import com.example.firstkotlin.constants.UrlConstant.TRANSACTION_URL
import com.example.firstkotlin.dto.TransactionDTO
import com.example.firstkotlin.dto.TransactionResponseDTO
import com.example.firstkotlin.exception.NoTransactionAmountException
import com.example.firstkotlin.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(TRANSACTION_URL)
class TransactionController(private val service: TransactionService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoTransactionAmount(e: NoTransactionAmountException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveTransaction(@RequestBody transactionDTO: TransactionDTO) = service.saveTransaction(transactionDTO)

    @GetMapping
    fun getTransactions(): TransactionResponseDTO = service.getTransactions()
}