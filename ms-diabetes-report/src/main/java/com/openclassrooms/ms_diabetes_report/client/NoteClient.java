package com.openclassrooms.ms_diabetes_report.client;

import com.openclassrooms.ms_diabetes_report.DTO.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-note", url = "http://ms-notes:8082")
public interface NoteClient {
    @GetMapping("/notes/{patId}")
    List<NoteDTO> getNotesByPatientId(@PathVariable("patId") int patId);
}
