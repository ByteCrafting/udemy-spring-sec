package br.com.woodriver.udemyspringsec.domain

import br.com.woodriver.udemyspringsec.domain.Roles.USER
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.apache.logging.log4j.util.Strings.EMPTY
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email"])
    ]
)
data class User(
    @Id
    private val username: String = EMPTY,
    @NotEmpty
    @Email
    var email: String = EMPTY,
    private var password: String = EMPTY,
    var enable: Boolean = true,

    @ManyToOne(fetch = EAGER, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    var role: Role = Role(id = 1, role = USER),
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(GrantedAuthority { role.role.name })
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    fun setPassword(password: String) {
        this.password = password
    }

    enum class UserFields {
        EMAIL, PASSWORD, ROLE
    }
}
