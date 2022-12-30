package com.example.firstkotlin.service

import com.example.firstkotlin.model.Role
import com.example.firstkotlin.repository.MongoRoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(private val repository: MongoRoleRepository) {
    fun saveRole(role: Role): Role = repository.save(role)
}