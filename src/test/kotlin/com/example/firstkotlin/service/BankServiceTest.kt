package com.example.firstkotlin.service

import com.example.firstkotlin.datasource.MongoBankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val mongoBankDataSource: MongoBankDataSource = mockk(relaxed = true)
    private val bankService = BankService(mongoBankDataSource)

    @Test
    fun `should call its data source retrieve banks`() {
        // when
        bankService.getBanks()

        // then
        verify(exactly = 1) { mongoBankDataSource.findAll()}
    }
}