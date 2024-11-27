package com.openclassrooms.ms_notes.service;

import com.openclassrooms.ms_notes.model.Note;
import com.openclassrooms.ms_notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest(NoteService.class)
public class NoteServiceTest {
    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteRepository noteRepository;

    public static Note note = new Note("1", 1, "TestNone", "This is a note test");

    public static List<Note> listOfNotes = new ArrayList<>();

    static {
        listOfNotes.add(note);
    }

    @Test
    public void testFindByPatientId() {
        when(noteRepository.findByPatId(anyInt())).thenReturn(listOfNotes);
        List<Note> result = noteService.findByPatientId(1);

        assertEquals(1, result.size());
        assertEquals("This is a note test", result.get(0).getNote());

        verify(noteRepository, times(1)).findByPatId(1);
    }

    @Test
    public void testCreateNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note createdNote = noteService.createNote(note);
        assertEquals(createdNote.getNote(), note.getNote());
    }
}
