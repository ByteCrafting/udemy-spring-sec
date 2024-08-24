package br.com.woodriver.udemyspringsec.repository

import br.com.woodriver.udemyspringsec.domain.Note
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository: JpaRepository <Note, Long> {
    fun findNoteByUsernameOwner(pageable: Pageable, username: String): Page<Note>
}
