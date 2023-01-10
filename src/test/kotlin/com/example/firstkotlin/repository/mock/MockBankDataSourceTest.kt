package com.example.firstkotlin.repository.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {

    private val mockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // when
        val banks = mockBankDataSource.getBanks()

        // then
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // when
        val banks = mockBankDataSource.getBanks()

        // then
        assertThat(banks).allMatch { it.id!!.toHexString().isNotBlank() }
        assertThat(banks).allMatch { it.name.isNotBlank() }
    }
}