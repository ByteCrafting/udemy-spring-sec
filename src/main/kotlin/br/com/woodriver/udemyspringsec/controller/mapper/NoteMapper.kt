package br.com.woodriver.udemyspringsec.controller.mapper

import br.com.woodriver.udemyspringsec.controller.request.NoteRequest
import br.com.woodriver.udemyspringsec.domain.Note


fun NoteRequest.toDomain(username: String) = Note(
    content = content,
    usernameOwner = username
)
