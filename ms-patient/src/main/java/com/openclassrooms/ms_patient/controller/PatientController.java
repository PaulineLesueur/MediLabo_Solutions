package com.openclassrooms.ms_patient.controller;

import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public Page<Patient> getAllPatients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return patientService.findAll(page, size);
    }

    @GetMapping("/patient/{id}")
    public Optional<Patient> getPatientById(@PathVariable Integer id) { return patientService.findById(id); }

    @PutMapping("/patient/{id}/update")
    public Patient updatePatient(@PathVariable Integer id, @RequestBody Patient updatedPatient) { return patientService.updatePatient(id, updatedPatient); }

    @PostMapping("/patients/create")
    public Patient createPatient(@RequestBody Patient newPatient) throws Exception { return patientService.createPatient(newPatient); }
}
