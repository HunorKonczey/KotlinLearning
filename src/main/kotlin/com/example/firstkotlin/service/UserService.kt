package com.example.firstkotlin.service

import com.example.firstkotlin.datasource.MongoUserDataSource
import com.example.firstkotlin.model.User
import org.springframework.stereotype.Repository

@Repository
class UserService(private val userDataSource: MongoUserDataSource) {
    fun saveUser(user: User): User = userDataSource.save(user)

    fun findByEmail(email: String): User? = userDataSource.findByEmail(email)
}