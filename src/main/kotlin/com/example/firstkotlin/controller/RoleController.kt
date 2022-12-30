package com.example.firstkotlin.controller

import com.example.firstkotlin.constants.UrlConstant.ROLES_URL
import com.example.firstkotlin.model.Role
import com.example.firstkotlin.service.RoleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ROLES_URL)
class RoleController(private val service: RoleService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody role: Role): Role = service.saveRole(role)
}