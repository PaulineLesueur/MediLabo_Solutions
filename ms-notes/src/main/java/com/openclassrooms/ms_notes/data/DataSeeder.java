package com.openclassrooms.ms_notes.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ms_notes.model.Note;
import com.openclassrooms.ms_notes.repository.NoteRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class DataSeeder implements CommandLineRunner {
    private final NoteRepository noteRepository;

    public DataSeeder(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        noteRepository.deleteAll();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Note>> typeReference = new TypeReference<List<Note>>() {};
        InputStream inputStream = new ClassPathResource("database/data.json").getInputStream();

        List<Map<String, Object>> rawNotes = mapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});

        rawNotes.forEach(note -> {
            if (note.containsKey("_id")) {
                note.put("id", note.remove("_id"));
            }
        });

        List<Note> notes = mapper.convertValue(rawNotes, typeReference);
        noteRepository.saveAll(notes);

    }
}
