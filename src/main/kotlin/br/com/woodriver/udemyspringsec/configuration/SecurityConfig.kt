package br.com.woodriver.udemyspringsec.configuration

import br.com.woodriver.udemyspringsec.domain.Roles.ADMIN
import br.com.woodriver.udemyspringsec.domain.Roles.USER
import br.com.woodriver.udemyspringsec.domain.User
import br.com.woodriver.udemyspringsec.domain.toDomain
import br.com.woodriver.udemyspringsec.repository.RoleRepository
import br.com.woodriver.udemyspringsec.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests.requestMatchers("/public/**").permitAll()
                requests.anyRequest().authenticated()
            }
        http.csrf { it.disable() }
        http.cors { it.disable() }
        http.httpBasic { }
        return http.build()
    }

    @Bean
    fun loadFirstData(): CommandLineRunner {
        if (!roleRepository.existsRoleByRole(USER)) {
            roleRepository.save(
                USER.toDomain()
            )
        }
        if (!roleRepository.existsRoleByRole(ADMIN)) {
            roleRepository.save(
                ADMIN.toDomain()
            )
        }
        if (!userRepository.existsUserByUsername("yanzika")) {
            userRepository.save(
                User(
                    username = "yanzika",
                    password = "{noop}123",
                    email = "yan@gmail.com",
                    role = roleRepository.findRoleByRole(ADMIN)
                )
            )
        }
        if (!userRepository.existsUserByUsername("user1")) {
            userRepository.save(
                User(
                    username = "user1",
                    password = "{noop}123",
                    email = "user1@gmail.com",
                    role = roleRepository.findRoleByRole(USER)
                )
            )
        }
        return CommandLineRunner {}
    }
}
