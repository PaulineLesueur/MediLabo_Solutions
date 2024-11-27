package com.openclassrooms.ms_notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.ms_notes.model.Note;
import com.openclassrooms.ms_notes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NoteController.class)
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private ObjectMapper objectMapper;

    public static Note note = new Note("1", 1, "TestNone", "This is a note test");

    public static List<Note> listOfNotes = new ArrayList<>();

    static {
        listOfNotes.add(note);
    }

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetNoteByPatientId() throws Exception {
        when(noteService.findByPatientId(anyInt())).thenReturn(listOfNotes);

        mockMvc.perform(get("/notes/{patId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestNone")))
                .andExpect(content().string(containsString("This is a note test")));
    }

    @Test
    public void testCreateNote() throws Exception {
        when(noteService.createNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/notes/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patient").value("TestNone"))
                .andExpect(jsonPath("$.note").value("This is a note test"));
    }

}
