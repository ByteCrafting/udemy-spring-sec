package br.com.woodriver.udemyspringsec.controller

import br.com.woodriver.udemyspringsec.domain.User
import br.com.woodriver.udemyspringsec.repository.UserRepository
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/users")
class UserController(
    private val userRepository: UserRepository,
) {

    @PostMapping
    fun signUp(@RequestBody user: User): ResponseEntity<User> {
        val userSaved = userRepository.save(user)

        return ResponseEntity(userSaved, CREATED)
    }
}
