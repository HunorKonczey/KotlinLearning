package com.example.firstkotlin.service

import com.example.firstkotlin.constants.FileConstant
import com.example.firstkotlin.dto.BankDTO
import com.example.firstkotlin.repository.mongo.BankRepository
import com.example.firstkotlin.model.Bank
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.io.path.pathString

@Service
class BankService(private val repository: BankRepository) {

    fun getBanks(): Collection<Bank> = repository.findAll()

    fun getBank(id: String): Bank = repository.findById(ObjectId(id)).orElse(null)

    fun addBank(bankDTO: BankDTO): Bank {
        val image: MultipartFile? = bankDTO.imageFile
        var imagePath = ""
        if (image != null && !image.isEmpty) {
            imagePath = uploadFile(image)
        }

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return repository.save(Bank(
            ObjectId(),
            bankDTO.bankName,
            formatter.parse(bankDTO.foundationDate),
            imagePath))
    }

    private fun uploadFile(image: MultipartFile): String {
        var imagePath = ""
        try {
            val bytes: ByteArray = image.bytes

            val currentDirectory: Path = Paths.get("").toAbsolutePath()
            val directoryToCreate: Path = currentDirectory.resolve(FileConstant.UPLOAD_BANK_PATH)
            if (Files.notExists(directoryToCreate)) {
                Files.createDirectories(directoryToCreate)
            }
            val imageName = "${UUID.randomUUID()}.${image.originalFilename?.split(".")!!.last()}"
            val uploadPath = Paths.get("${directoryToCreate.pathString}/${imageName}")
            imagePath = "${FileConstant.UPLOAD_BANK_PATH}/$imageName"
            Files.write(uploadPath, bytes)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return imagePath
    }

    fun updateBank(bankDTO: BankDTO): Bank {
        val bank = getBank(bankDTO.id)
        if (bankDTO.bankName != bank.name) {
            bank.name = bankDTO.bankName
        }
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val formattedFoundationDate = formatter.parse(bankDTO.foundationDate)
        if (formattedFoundationDate != bank.foundationDate) {
            bank.foundationDate = formattedFoundationDate
        }
        val imageFile = bankDTO.imageFile
        if (imageFile != null && !imageFile.isEmpty) {
            bank.imagePath = uploadFile(bankDTO.imageFile!!)
        }
        return repository.save(bank)
    }

    fun deleteBankById(id: String) = repository.deleteById(ObjectId(id))
}