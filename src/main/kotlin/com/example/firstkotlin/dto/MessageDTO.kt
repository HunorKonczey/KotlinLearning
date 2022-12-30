package com.example.firstkotlin.dto

import com.example.firstkotlin.enum.MessageStatus

data class MessageDTO(val message: String, val status: MessageStatus = MessageStatus.SUCCESS)
