package com.example.firstkotlin.controller

import com.example.firstkotlin.constants.UrlConstant.BANKS_URL
import com.example.firstkotlin.dto.AmountDTO
import com.example.firstkotlin.dto.BankDTO
import com.example.firstkotlin.model.Bank
import com.example.firstkotlin.service.BankService
import com.example.firstkotlin.service.UserBankService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping(BANKS_URL)
class BankController(private val service: BankService, private val userBankService: UserBankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @Operation(operationId = "getBanks")
    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()

    @Operation(operationId = "getBank")
    @GetMapping("/{id}")
    fun getBank(@PathVariable id: String) = service.getBank(id)

    @Operation(operationId = "addBank")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: BankDTO): Bank = service.addBank(bank)

    @Operation(operationId = "updateBank")
    @PatchMapping
    fun updateBank(@RequestBody bank: Bank): Bank = service.updateBank(bank)

    @Operation(operationId = "deleteBank")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable id: String) = service.deleteBankById(id)

    @Operation(operationId = "saveUserBank")
    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUserBank(@RequestParam bankId: String) = userBankService.saveUserBank(bankId)

    @Operation(operationId = "getUserBanks")
    @GetMapping("user")
    fun getUserBanks() = userBankService.getUserBanks()

    @Operation(operationId = "getUserBanksWithAmounts")
    @GetMapping("user/amounts")
    fun getUserBanksWithAmounts() = userBankService.getUserBanksWithAmounts()

    @Operation(operationId = "getUserBanksWithoutLoggedUser")
    @GetMapping("user/others")
    fun getUserBanksWithoutLoggedUser() = userBankService.getUserBanksWithoutLoggedUser()

    @Operation(operationId = "addAmount")
    @PostMapping("user/amount")
    fun addAmount(@RequestBody amountDTO : AmountDTO) = userBankService.addAmount(amountDTO)

    @Operation(operationId = "deleteUserBank")
    @GetMapping("user/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUserBank(@PathVariable id: String) = userBankService.deleteById(id)
}