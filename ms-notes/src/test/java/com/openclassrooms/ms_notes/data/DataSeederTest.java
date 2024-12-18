package com.openclassrooms.ms_notes.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ms_notes.data.DataSeeder;
import com.openclassrooms.ms_notes.model.Note;
import com.openclassrooms.ms_notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class DataSeederTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private DataSeeder dataSeeder;

    @Test
    void testRun() throws Exception {
        MockitoAnnotations.openMocks(this);

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new ClassPathResource("database/data.json").getInputStream();
        List<Map<String, Object>> rawNotes = objectMapper.readValue(inputStream, new TypeReference<>() {});


        rawNotes.forEach(note -> {
            if (note.containsKey("_id")) {
                note.put("id", note.remove("_id"));
            }
        });

        List<Note> notes = objectMapper.convertValue(rawNotes, new TypeReference<List<Note>>() {});

        dataSeeder.run();

        verify(noteRepository).deleteAll();
        verify(noteRepository).saveAll(anyList());
    }
}
