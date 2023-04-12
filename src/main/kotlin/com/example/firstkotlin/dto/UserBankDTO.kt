package com.example.firstkotlin.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class UserBankDTO(val userBankId: String,
                       val userBankAmountId: String,
                       val bankName: String,
                       val accountAmount: Double,
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                       val createdDate: Date)
