package com.example.firstkotlin.controller

import com.example.firstkotlin.constants.JwtConstant
import com.example.firstkotlin.constants.UrlConstant.USERS_URL
import com.example.firstkotlin.dto.MessageDTO
import com.example.firstkotlin.dto.RegisterDTO
import com.example.firstkotlin.enum.MessageStatus
import com.example.firstkotlin.model.User
import com.example.firstkotlin.service.UserService
import com.example.firstkotlin.util.JwtUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(USERS_URL)
class UserController(private val userService: UserService) {

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User.ModelMapper.from(body)
        return ResponseEntity.ok(userService.saveUser(user))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = userService.deleteUserById(id)

    @GetMapping("token/refresh")
    fun refresh(request: HttpServletRequest, response: HttpServletResponse) {
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith(JwtConstant.TOKEN_PREFIX)) {
            try {
                val refreshToken = authorizationHeader.substring(JwtConstant.TOKEN_PREFIX.length)
                val decodeJWT = JwtUtil.decodeExistingJwt(refreshToken)
                val userName = decodeJWT.subject
                val user = userService.findByEmail(userName)
                val accessToken = JwtUtil.createAccessJwtToken(user!!, request.requestURL.toString())
                val tokens = HashMap<String, String>()
                tokens["access_token"] = accessToken
                tokens["refresh_token"] = refreshToken

                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, tokens)
            } catch (ex: Exception) {
                response.setHeader("error", ex.message)
                response.status = HttpStatus.FORBIDDEN.value()

                val error = HashMap<String, MessageDTO>()
                error["error_message"] = MessageDTO(ex.message!!, MessageStatus.ERROR)

                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, error)
            }
        } else {
            throw RuntimeException("Refresh token is missing!")
        }
    }
}