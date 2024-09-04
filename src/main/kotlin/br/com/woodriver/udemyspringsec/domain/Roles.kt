package br.com.woodriver.udemyspringsec.domain

enum class Roles {
    ROLE_ADMIN, ROLE_USER
}

fun Roles.toDomain() = Role(role = this)
