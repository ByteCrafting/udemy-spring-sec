package br.com.woodriver.udemyspringsec.repository

import br.com.woodriver.udemyspringsec.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findUserByUsername(username: String): User
    fun existsUserByUsername(username: String): Boolean
}
