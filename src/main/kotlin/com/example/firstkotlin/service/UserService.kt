package com.example.firstkotlin.service

import com.example.firstkotlin.repository.MongoRoleRepository
import com.example.firstkotlin.repository.MongoUserRepository
import com.example.firstkotlin.enum.RoleType
import com.example.firstkotlin.model.Role
import com.example.firstkotlin.model.User
import org.bson.types.ObjectId
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.jvm.Throws

@Service
class UserService(private val repository: MongoUserRepository, private val roleRepository: MongoRoleRepository) : UserDetailsService {
    fun saveUser(user: User): User {
        val role: Role? = roleRepository.findByName(RoleType.USER.name).orElse(null)
        val roleAdmin: Role? = roleRepository.findByName(RoleType.ADMIN.name).orElse(null)
        if (role != null) user.roleIds.add(role)
        if (roleAdmin != null) user.roleIds.add(roleAdmin)
        return repository.save(user)
    }

    fun findByEmail(email: String): User? = repository.findByEmail(email).orElse(null)

    fun deleteUserById(id: String) = repository.deleteById(ObjectId(id))

    fun findByUserId(id: String): User? = repository.findById(ObjectId(id)).orElse(null)

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByEmail(username).orElse(null)
        user ?: throw UsernameNotFoundException("UserNotFound in database")

        val authorities: Collection<SimpleGrantedAuthority> = user.roleIds.stream()
            .map { role -> SimpleGrantedAuthority(role.name.name) }
            .collect(Collectors.toList())
        return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
    }
}