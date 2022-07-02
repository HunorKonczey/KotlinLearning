package com.example.firstkotlin.datasource

import com.example.firstkotlin.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
}
