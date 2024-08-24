package br.com.woodriver.udemyspringsec.service

import br.com.woodriver.udemyspringsec.domain.Note
import br.com.woodriver.udemyspringsec.repository.NoteRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NoteService(
    val noteRepository: NoteRepository
) {

    fun getPaginatedNotes(pageable: Pageable, username: String) = noteRepository.findNoteByUsernameOwner(
        pageable,
        username
    )

    fun createNote(note: Note) = noteRepository.save(note)

    fun updateNote(id: Long, content: String): Note {
        lateinit var noteToUpdate: Note
        val note = noteRepository.findById(id)

        if (note.isPresent) {
            noteToUpdate = note.get()
            noteToUpdate.content = content
            noteRepository.save(noteToUpdate)
        } else {
            throw RuntimeException("Note not found")
        }

        return noteToUpdate
    }

    fun deleteNote(id: Long) = noteRepository.delete(Note(id = id))
}
