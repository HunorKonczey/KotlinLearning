package com.example.firstkotlin.filter

import com.example.firstkotlin.dto.UserDTO
import com.example.firstkotlin.service.UserService
import com.example.firstkotlin.util.JwtUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(private val authenticationManager: AuthenticationManager, private val userService: UserService) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val username = request?.getParameter("email")
        val password = request?.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        authenticationToken.details = userService.findByEmail(username!!)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val userDetails = authResult!!.principal as User
        val accessToken = JwtUtil.createAccessJwtToken(userDetails, request!!.requestURL.toString())
        val refreshToken = JwtUtil.createRefreshJwtToken(userDetails, request.requestURL.toString())
        val user = userService.findByEmail(userDetails.username!!)
        val userDTO = UserDTO(
            user!!.email,
            user.roleIds.stream()
                .map { role -> role.name }
                .collect(Collectors.toList()),
            refreshToken,
            accessToken)

        response!!.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, userDTO)
    }
}