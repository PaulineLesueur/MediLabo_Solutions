package com.openclassrooms.ms_patient.controller;

import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public ResponseEntity<Page<Patient>> getAllPatients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Patient> patientsList = patientService.findAll(page, size);

        if(patientsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(patientsList.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(patientsList.getTotalPages()))
                .body(patientsList);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer id) {
        Patient patient = patientService.findById(id);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/patient/{id}/update")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer id, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(id, updatedPatient);
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/patients/create")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient newPatient) {
        Patient patient = patientService.createPatient(newPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }
}
