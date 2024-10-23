package com.openclassrooms.ms_patient.controller;

import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public Iterable<Patient> getAllPatients() {
        return patientService.findAll();
    }
}
