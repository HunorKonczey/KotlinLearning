package com.example.firstkotlin.dto

import org.springframework.web.multipart.MultipartFile

data class BankDTO(var id: String = "",
                   val bankName: String,
                   val foundationDate: String,
                   var imageFile: MultipartFile? = null)