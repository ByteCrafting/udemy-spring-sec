package br.com.woodriver.udemyspringsec.domain

enum class Roles {
    ADMIN, USER
}

fun Roles.toDomain() = Role(role = this)
