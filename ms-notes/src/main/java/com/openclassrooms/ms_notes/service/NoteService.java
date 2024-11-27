package com.openclassrooms.ms_notes.service;

import com.openclassrooms.ms_notes.model.Note;
import com.openclassrooms.ms_notes.repository.NoteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private static final Logger logger = LogManager.getLogger(NoteService.class);

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findByPatientId(Integer patId) {
        return noteRepository.findByPatId(patId);
    }

    public Note createNote(Note newNote) {
        Note savedNote = noteRepository.save(newNote);
        logger.info("Successfully created note for the patient {}", savedNote.getPatient());
        return savedNote;
    }
}
