package br.com.woodriver.udemyspringsec.controller

import br.com.woodriver.udemyspringsec.domain.User
import br.com.woodriver.udemyspringsec.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/")
class UserController(
    private val userRepository: UserRepository,
) {

    @PostMapping("/users/signup")
    fun signUp(@RequestBody user: User): ResponseEntity<User> {
        val userSaved = userRepository.save(user)

        return ResponseEntity(userSaved, CREATED)
    }

    @GetMapping("/csrf-token")
    fun getCsrfToken(request: HttpServletRequest): ResponseEntity<CsrfToken> {
        return ResponseEntity.ok(request.getAttribute(CsrfToken::class.qualifiedName) as CsrfToken)
    }
}
