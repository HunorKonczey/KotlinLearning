package com.example.firstkotlin.filter

import com.example.firstkotlin.constants.JwtConstant.TOKEN_PREFIX
import com.example.firstkotlin.constants.UrlConstant.LOGIN_URL
import com.example.firstkotlin.constants.UrlConstant.REFRESH_URL
import com.example.firstkotlin.dto.MessageDTO
import com.example.firstkotlin.enum.MessageStatus
import com.example.firstkotlin.util.JwtUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Arrays.stream
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (listOf("/$LOGIN_URL", "/$REFRESH_URL").contains(request.servletPath)) {
            filterChain.doFilter(request, response)
        } else {
            val authorizationHeader = request.getHeader(AUTHORIZATION)
            if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
                try {
                    val token = authorizationHeader.substring(TOKEN_PREFIX.length)
                    val decodeJWT = JwtUtil.decodeExistingJwt(token)
                    val userName = decodeJWT.subject
                    val roles = decodeJWT.getClaim("roles").asArray(String::class.java)
                    val authorities = stream(roles)
                        .map { role -> SimpleGrantedAuthority(role) }
                        .collect(Collectors.toList())
                    val authenticationToken = UsernamePasswordAuthenticationToken(userName, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (ex: Exception) {
                    logger.info("Error logging in", ex)
                    response.setHeader("error", ex.message)
                    response.status = HttpStatus.FORBIDDEN.value()

                    val error = HashMap<String, MessageDTO>()
                    error["error_message"] = MessageDTO(ex.message!!, MessageStatus.ERROR)

                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }

}