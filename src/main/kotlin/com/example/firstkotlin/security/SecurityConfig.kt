package com.example.firstkotlin.security

import com.example.firstkotlin.constants.UrlConstant.BANKS_URL
import com.example.firstkotlin.constants.UrlConstant.LOGIN_URL
import com.example.firstkotlin.constants.UrlConstant.REFRESH_URL
import com.example.firstkotlin.constants.UrlConstant.REGISTER_URL
import com.example.firstkotlin.constants.UrlConstant.ROLES_URL
import com.example.firstkotlin.enum.RoleType
import com.example.firstkotlin.filter.CustomAuthenticationFilter
import com.example.firstkotlin.filter.CustomAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {
    val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val customAuthFilter = CustomAuthenticationFilter(authenticationManagerBean())
        customAuthFilter.setFilterProcessesUrl("/$LOGIN_URL")
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests().antMatchers("/$REGISTER_URL/**", "/$LOGIN_URL/**", "/$REFRESH_URL/**")
            .permitAll()
        http.authorizeRequests().antMatchers("/$BANKS_URL", "/$ROLES_URL")
            .hasAnyAuthority(RoleType.ADMIN.name)
        http.authorizeRequests().anyRequest().authenticated()
        http.addFilter(customAuthFilter)
        http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}