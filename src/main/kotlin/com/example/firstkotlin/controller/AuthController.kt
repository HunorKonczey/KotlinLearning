package com.example.firstkotlin.controller

import com.example.firstkotlin.dtos.LoginDTO
import com.example.firstkotlin.dtos.MessageDTO
import com.example.firstkotlin.dtos.RegisterDTO
import com.example.firstkotlin.model.User
import com.example.firstkotlin.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class AuthController(private val userService: UserService) {

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User.ModelMapper.from(body)
        return ResponseEntity.ok(userService.saveUser(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO): ResponseEntity<Any> {
        val user = userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(MessageDTO("user not found!"))
        if (!user.comparePassword(body.password))
            return ResponseEntity.badRequest().body(MessageDTO("invalid password!"))
        return ResponseEntity.ok(user)
    }
}