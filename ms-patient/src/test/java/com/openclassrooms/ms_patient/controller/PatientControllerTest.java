package com.openclassrooms.ms_patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private ObjectMapper objectMapper;

    Patient patient1 = new Patient(1, "Doe", "John", LocalDate.of(1990, 5, 25), "M", "123 Test Road", "111-222-3333");
    Patient patient2 = new Patient(2, "Smith", "Jane", LocalDate.of(1980, 11, 6), "F", "456 Test Road", "222-333-4444");

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetAllPatients_ReturnsPatientsList() throws Exception {
        List<Patient> patientsList = List.of(patient1, patient2);
        Page<Patient> patientPage = new PageImpl<>(patientsList);

        when(patientService.findAll(0, 10)).thenReturn(patientPage);

        mockMvc.perform(get("/patients?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", String.valueOf(patientsList.size())))
                .andExpect(header().string("X-Total-Pages", "1"))
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[1].firstName").value("Jane"));
    }

    @Test
    public void testGetAllPatients_NoContent() throws Exception {
        Page<Patient> emptyPage = new PageImpl<>(List.of());

        when(patientService.findAll(anyInt(), anyInt())).thenReturn(emptyPage);

        mockMvc.perform(get("/patients?page=0&size=10"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetPatientById() throws Exception {
        when(patientService.findById(anyInt())).thenReturn(patient1);

        mockMvc.perform(get("/patient/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }


    @Test
    public void testCreatePatient() throws Exception {
        when(patientService.createPatient(any(Patient.class))).thenReturn(patient1);

        mockMvc.perform(post("/patients/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(patient1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Integer patientId = Math.toIntExact(patient1.getId());
        Patient updatedPatient = new Patient(1, "Smith", "John", LocalDate.of(1990, 5, 25), "M", "123 Test Road", "111-222-3333");

        when(patientService.updatePatient(eq(patientId), any(Patient.class))).thenReturn(updatedPatient);

        mockMvc.perform(put("/patient/{id}/update", patientId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Smith"));

    }

}
