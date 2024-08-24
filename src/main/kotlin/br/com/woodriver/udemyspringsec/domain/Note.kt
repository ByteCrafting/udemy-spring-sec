package br.com.woodriver.udemyspringsec.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import org.apache.logging.log4j.util.Strings.EMPTY

@Entity
@Table(name = "notes")
data class Note(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    @Lob
    var content: String = EMPTY,
    val usernameOwner: String = EMPTY
)
