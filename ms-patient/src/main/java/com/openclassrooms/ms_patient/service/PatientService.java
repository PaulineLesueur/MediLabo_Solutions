package com.openclassrooms.ms_patient.service;

import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Iterable<Patient> findAll() {
        return patientRepository.findAll();
    }
}
