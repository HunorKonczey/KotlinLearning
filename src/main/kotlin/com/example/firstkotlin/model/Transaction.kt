package com.example.firstkotlin.model

import com.example.firstkotlin.enum.TransactionStatus
import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*

@Document("transactions")
open class Transaction(
    @MongoId
    var id: ObjectId? = ObjectId(),
    @DBRef(lazy = false)
    val senderUserBank: UserBank,
    @DBRef(lazy = false)
    val receiverUserBank: UserBank,
    val transactionValue: Double,
    val transactionStatus: TransactionStatus,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    val transactionDate: Date
)