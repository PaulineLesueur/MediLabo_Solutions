package com.openclassrooms.ms_patient.service;

import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Iterable<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(Integer id) { return patientRepository.findById(id); }

    public Patient updatePatient(Integer id, Patient updatedPatient) {
        Optional<Patient> patient = findById(id);

        if(patient.isPresent()) {
            Patient patientToUpdate = patient.get();

            if(updatedPatient.getLastName() != null) {
                patientToUpdate.setLastName(updatedPatient.getLastName());
            }

            if(updatedPatient.getFirstName() != null) {
                patientToUpdate.setFirstName(updatedPatient.getFirstName());
            }

            if(updatedPatient.getBirthdate() != null) {
                patientToUpdate.setBirthdate(updatedPatient.getBirthdate());
            }

            if(updatedPatient.getGender() != null) {
                patientToUpdate.setGender(updatedPatient.getGender());
            }

            if(updatedPatient.getAddress() != null) {
                patientToUpdate.setAddress(updatedPatient.getAddress());
            }

            if(updatedPatient.getPhone() != null) {
                patientToUpdate.setPhone(updatedPatient.getPhone());
            }

            return patientRepository.save(patientToUpdate);
        } else {
            throw new RuntimeException("Patient not found");
        }
    }
}
