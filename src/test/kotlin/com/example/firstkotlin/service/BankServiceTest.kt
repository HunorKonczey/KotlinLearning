package com.example.firstkotlin.service

import com.example.firstkotlin.repository.mongo.BankRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val mongoBankRepository: BankRepository = mockk(relaxed = true)
    private val bankService = BankService(mongoBankRepository)

    @Test
    fun `should call its data source retrieve banks`() {
        // when
        bankService.getBanks()

        // then
        verify(exactly = 1) { mongoBankRepository.findAll()}
    }
}