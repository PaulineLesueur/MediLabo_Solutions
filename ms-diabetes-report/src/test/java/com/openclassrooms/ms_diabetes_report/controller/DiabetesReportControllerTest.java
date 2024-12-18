package com.openclassrooms.ms_diabetes_report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ms_diabetes_report.DTO.NoteDTO;
import com.openclassrooms.ms_diabetes_report.DTO.PatientDTO;
import com.openclassrooms.ms_diabetes_report.client.NoteClient;
import com.openclassrooms.ms_diabetes_report.client.PatientClient;
import com.openclassrooms.ms_diabetes_report.enums.RiskLevel;
import com.openclassrooms.ms_diabetes_report.service.DiabetesRiskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DiabetesReportController.class)
public class DiabetesReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientClient patientClient;

    @MockBean
    private NoteClient noteClient;

    @MockBean
    private DiabetesRiskService diabetesRiskService;

    PatientDTO patient = new PatientDTO(1, "Doe", "John", LocalDate.of(1990, 5, 25), "M", "123 Willow Creek", "222-333-4444");
    List<NoteDTO> notes = List.of(new NoteDTO("1", 1, "Doe John", "No symptom"));

    @Test
    public void testGetDiabetesRisk_ReturnsRiskLevel() throws Exception {
        when(patientClient.getPatientById(anyInt())).thenReturn(patient);
        when(noteClient.getNotesByPatientId(anyInt())).thenReturn(notes);
        when(diabetesRiskService.calculateRisk(anyInt(), anyString(), anyList())).thenReturn(RiskLevel.NONE);

        mockMvc.perform(get("/diabetes-report/{patId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("\"NONE\""));

        verify(patientClient, times(1)).getPatientById(1);
        verify(noteClient, times(1)).getNotesByPatientId(1);
        verify(diabetesRiskService, times(1)).calculateRisk(anyInt(), anyString(), anyList());
    }
}