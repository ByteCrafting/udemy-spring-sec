package br.com.woodriver.udemyspringsec.configuration.security

import br.com.woodriver.udemyspringsec.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findUserByUsername(username)
    }
}
