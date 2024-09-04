package br.com.woodriver.udemyspringsec.configuration

import br.com.woodriver.udemyspringsec.domain.Roles.ROLE_ADMIN
import br.com.woodriver.udemyspringsec.domain.Roles.ROLE_USER
import br.com.woodriver.udemyspringsec.domain.User
import br.com.woodriver.udemyspringsec.domain.toDomain
import br.com.woodriver.udemyspringsec.repository.RoleRepository
import br.com.woodriver.udemyspringsec.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true,
)
class SecurityConfig(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests.requestMatchers("/public/**").permitAll()
                requests.requestMatchers("/error/**").permitAll()
                requests.anyRequest().authenticated()
            }
        http.csrf { csrf ->
            csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        }
        http.cors { it.disable() }
        http.httpBasic { }
        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun loadFirstData(passwordEncoder: PasswordEncoder): CommandLineRunner {
        if (!roleRepository.existsRoleByRole(ROLE_USER)) {
            roleRepository.save(
                ROLE_USER.toDomain()
            )
        }
        if (!roleRepository.existsRoleByRole(ROLE_ADMIN)) {
            roleRepository.save(
                ROLE_ADMIN.toDomain()
            )
        }
        if (!userRepository.existsUserByUsername("yanzika")) {
            userRepository.save(
                User(
                    username = "yanzika",
                    password = passwordEncoder.encode("123"),
                    email = "yan@gmail.com",
                    role = roleRepository.findRoleByRole(ROLE_ADMIN)
                )
            )
        }
        if (!userRepository.existsUserByUsername("user1")) {
            userRepository.save(
                User(
                    username = "user1",
                    password = passwordEncoder.encode("123"),
                    email = "user1@gmail.com",
                    role = roleRepository.findRoleByRole(ROLE_USER)
                )
            )
        }
        return CommandLineRunner {}
    }
}
