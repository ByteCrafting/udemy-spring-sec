package br.com.woodriver.udemyspringsec.domain

import br.com.woodriver.udemyspringsec.domain.Roles.USER
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "rol_id")
    val id: Int = 0,
    @Column(name = "rol_name")
    val role: Roles = USER,
    @OneToMany(mappedBy = "role", fetch = EAGER, cascade = [CascadeType.MERGE])
    val users: Set<User> = hashSetOf()

)
