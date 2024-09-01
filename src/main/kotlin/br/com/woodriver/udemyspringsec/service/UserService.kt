package br.com.woodriver.udemyspringsec.service

import br.com.woodriver.udemyspringsec.domain.Roles
import br.com.woodriver.udemyspringsec.domain.User
import br.com.woodriver.udemyspringsec.domain.User.UserFields.EMAIL
import br.com.woodriver.udemyspringsec.domain.User.UserFields.PASSWORD
import br.com.woodriver.udemyspringsec.domain.User.UserFields.ROLE
import br.com.woodriver.udemyspringsec.repository.RoleRepository
import br.com.woodriver.udemyspringsec.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val roleRepository: RoleRepository,
    val userRepository: UserRepository,
) {

    fun updateUserField(user: User, field: User.UserFields, value: String) {
        when (field) {
            ROLE -> user.role = roleRepository.findRoleByRole(Roles.valueOf(value.uppercase()))
            PASSWORD -> user.password = value
            EMAIL -> user.email = value
        }

        userRepository.save(user)
    }
}
