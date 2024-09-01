package br.com.woodriver.udemyspringsec.controller

import br.com.woodriver.udemyspringsec.domain.User
import br.com.woodriver.udemyspringsec.domain.User.UserFields
import br.com.woodriver.udemyspringsec.repository.UserRepository
import br.com.woodriver.udemyspringsec.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val userRepository: UserRepository,
    private val userService: UserService,
) {
    @GetMapping("/users")
    fun getAllUsers(page: Pageable): ResponseEntity<Page<User>> {
        return ResponseEntity<Page<User>>(
            userRepository.findAll(page),
            OK
        )
    }

    @PatchMapping("/users/{username}")
    fun updateUserRole(
        @PathVariable username: String,
        @RequestBody fields: Map<String, String>
    ): ResponseEntity<User> {
        val user = userRepository.findUserByUsername(username)

        if (fields.isEmpty()) {
            throw RuntimeException("You must provide at least one field to update")
        }
        if (fields.size > 1) {
            throw RuntimeException("You can only update one field at a time")
        }

        fields.firstNotNullOf {
            val field = UserFields.valueOf(it.key.uppercase())
            userService.updateUserField(user, field, it.value)
        }

        return ResponseEntity(user, OK)
    }

    @GetMapping("/users/{username}")
    fun getUser(@PathVariable username: String): ResponseEntity<User> {
        return ResponseEntity<User>(
            userRepository.findUserByUsername(username),
            OK
        )
    }
}
