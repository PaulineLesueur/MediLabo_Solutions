package com.openclassrooms.ms_notes.controller;

import com.openclassrooms.ms_notes.model.Note;
import com.openclassrooms.ms_notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/notes/{patId}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable Integer patId) {
        List<Note> notes = noteService.findByPatientId(patId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/notes/create")
    public ResponseEntity<Note> createNote(@RequestBody Note newNote) {
        Note note = noteService.createNote(newNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }
}
