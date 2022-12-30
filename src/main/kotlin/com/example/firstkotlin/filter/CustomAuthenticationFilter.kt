package com.example.firstkotlin.filter

import com.example.firstkotlin.util.JwtUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(private val authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val username = request?.getParameter("email")
        val password = request?.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val user = authResult!!.principal as User
        val tokens = HashMap<String, String>()
        tokens["access_token"] = JwtUtil.createAccessJwtToken(user, request!!.requestURL.toString())
        tokens["refresh_token"] = JwtUtil.createRefreshJwtToken(user, request.requestURL.toString())

        response!!.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
    }
}