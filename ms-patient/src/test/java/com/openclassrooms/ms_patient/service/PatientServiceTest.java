package com.openclassrooms.ms_patient.service;

import com.openclassrooms.ms_patient.exceptions.DuplicatePatientException;
import com.openclassrooms.ms_patient.exceptions.ResourceNotFoundException;
import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(PatientService.class)
public class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    Patient patient1 = new Patient(1, "Doe", "John", LocalDate.of(1990, 5, 25), "M", "123 Test Road", "111-222-3333");

    Patient patient2 = new Patient(2, "Smith", "Jane", LocalDate.of(1980, 11, 6), "F", "456 Test Road", "111-222-3333");

    @Test
    public void testFindAll() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Patient> patientList = List.of(patient1, patient2);
        Page<Patient> mockPage = new PageImpl<>(patientList, pageable, patientList.size());

        when(patientRepository.findAll(pageable)).thenReturn(mockPage);
        Page<Patient> result = patientService.findAll(page, size);

        assertThat(result).isEqualTo(mockPage);
        verify(patientRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindById_ReturnsPatient() {
        when(patientRepository.findById(anyInt())).thenReturn(Optional.ofNullable(patient1));

        Patient patientFound = patientService.findById(1);
        assertEquals(patientFound.getId(), patient1.getId());
    }

    @Test
    public void testFindById_ThrowsException() {
        Integer invalidId = 3;

        when(patientRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> patientService.findById(invalidId))
                .withMessage("Patient with ID " + invalidId + " not found");

        verify(patientRepository, times(1)).findById(invalidId);
    }

    @Test
    public void testCreatePatient_ReturnsPatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient1);

        Patient createdPatient = patientService.createPatient(patient1);
        assertEquals(createdPatient.getId(), patient1.getId());
    }

    @Test
    public void testCreatePatient_ThrowsException() {
        when(patientRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(patient1));

        assertThatExceptionOfType(DuplicatePatientException.class)
                .isThrownBy(() -> patientService.createPatient(patient1))
                .withMessage("There already is a patient with this first and last name");

        verify(patientRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void testUpdatePatient() {
        Integer patientId = 1;

        Patient patient = new Patient(patientId, "Doe", "John", LocalDate.of(1990, 5, 25), "M", "123 Test Road", "111-222-3333");

        Patient updatedPatient = new Patient(patientId, "Smith", "Jane", null, "F", null, null);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.updatePatient(patientId, updatedPatient);

        assertThat(result.getFirstName()).isEqualTo("Jane");
        assertThat(result.getLastName()).isEqualTo("Smith");
        assertThat(result.getPhone()).isEqualTo(patient.getPhone());

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).save(patient);
    }

}
