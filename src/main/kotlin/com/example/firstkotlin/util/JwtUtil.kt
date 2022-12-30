package com.example.firstkotlin.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.firstkotlin.constants.JwtConstant
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

class JwtUtil {
    companion object {
        private val algorithm: Algorithm = Algorithm.HMAC256(JwtConstant.SECRET_KEY)

        fun createAccessJwtToken(user: User, issuer: String) : String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10)))
            .withIssuer(issuer)
            .withClaim("roles", user.authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
            .sign(algorithm)

        fun createAccessJwtToken(user: com.example.firstkotlin.model.User, issuer: String) : String = JWT.create()
            .withSubject(user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10)))
            .withIssuer(issuer)
            .withClaim("roles", user.roleIds.stream()
                .map { role -> role.name.name }
                .collect(Collectors.toList()))
            .sign(algorithm)

        fun createRefreshJwtToken(user: User, issuer: String) : String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(100)))
            .withIssuer(issuer)
            .sign(algorithm)

        fun decodeExistingJwt(token: String): DecodedJWT = JWT.require(algorithm)
            .build()
            .verify(token)
    }
}