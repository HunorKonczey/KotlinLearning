package com.example.firstkotlin.controller

import com.example.firstkotlin.constants.UrlConstant.TRANSACTION_URL
import com.example.firstkotlin.dto.TransactionDTO
import com.example.firstkotlin.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(TRANSACTION_URL)
class TransactionController(private val service: TransactionService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveTransaction(@RequestBody transactionDTO: TransactionDTO) = service.saveTransaction(transactionDTO)
}