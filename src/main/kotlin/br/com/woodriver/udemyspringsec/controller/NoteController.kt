package br.com.woodriver.udemyspringsec.controller

import br.com.woodriver.udemyspringsec.controller.mapper.toDomain
import br.com.woodriver.udemyspringsec.controller.request.NoteRequest
import br.com.woodriver.udemyspringsec.domain.Note
import br.com.woodriver.udemyspringsec.service.NoteService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteService: NoteService
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(NoteController::class.java)
    }

    @PostMapping
    fun createNote(
        @RequestBody noteRequest: NoteRequest,
        @AuthenticationPrincipal user: UserDetails
    ): ResponseEntity<Note> {
        logger.info("Starting to create a new note for [user=${user.username}]")
        val note = noteService.createNote(noteRequest.toDomain(user.username)).also {
            logger.info("Done to create note successfully with [id=${it.id}]")
        }
        return ResponseEntity(note, CREATED)
    }

    @PutMapping("/{id}")
    fun updateNote(@RequestBody noteRequest: NoteRequest, @AuthenticationPrincipal user: UserDetails,
                   @PathVariable id: Long
    ): ResponseEntity<Note> {
        logger.info("Starting to update note for [user=${user.username}]")
        val note = noteService.updateNote(id, noteRequest.content).also {
            logger.info("Done to update note successfully")
        }
        return ResponseEntity.ok(note)
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@AuthenticationPrincipal user: UserDetails,
                   @PathVariable id: Long
    ) {
        logger.info("Starting to delete note for [user=${user.username}]")
        noteService.deleteNote(id).also {
            logger.info("Done to delete note successfully")
        }
    }

    @GetMapping
    fun getPaginatedNotes(page: Pageable, @AuthenticationPrincipal user: UserDetails) = noteService.getPaginatedNotes(
        page,
        user.username
    )
}
