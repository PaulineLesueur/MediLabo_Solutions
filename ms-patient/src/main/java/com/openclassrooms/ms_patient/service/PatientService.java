package com.openclassrooms.ms_patient.service;

import com.openclassrooms.ms_patient.exceptions.DuplicatePatientException;
import com.openclassrooms.ms_patient.exceptions.ResourceNotFoundException;
import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Page<Patient> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable);
    }

    public Patient findById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + id + " not found"));
    }

    public Patient createPatient(Patient newPatient) {
        if(patientRepository.findByFirstNameAndLastName(newPatient.getFirstName(), newPatient.getLastName()).isPresent()) {
            throw new DuplicatePatientException("There already is a patient with this first and last name");
        }

        return patientRepository.save(newPatient);
    }

    public Patient updatePatient(Integer id, Patient updatedPatient) {
        Patient patientToUpdate = findById(id);

        updateIfNotNull(updatedPatient.getLastName(), patientToUpdate::setLastName);
        updateIfNotNull(updatedPatient.getFirstName(), patientToUpdate::setFirstName);
        updateIfNotNull(updatedPatient.getBirthdate(), patientToUpdate::setBirthdate);
        updateIfNotNull(updatedPatient.getGender(), patientToUpdate::setGender);
        updateIfNotNull(updatedPatient.getAddress(), patientToUpdate::setAddress);
        updateIfNotNull(updatedPatient.getPhone(), patientToUpdate::setPhone);

        return patientRepository.save(patientToUpdate);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> updater) {
        if (value != null) {
            updater.accept(value);
        }
    }
}
