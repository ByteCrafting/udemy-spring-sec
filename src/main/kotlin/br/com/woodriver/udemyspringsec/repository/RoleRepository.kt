package br.com.woodriver.udemyspringsec.repository

import br.com.woodriver.udemyspringsec.domain.Role
import br.com.woodriver.udemyspringsec.domain.Roles
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Int> {
    fun existsRoleByRole(role: Roles): Boolean
    fun findRoleByRole(role: Roles): Role
}
