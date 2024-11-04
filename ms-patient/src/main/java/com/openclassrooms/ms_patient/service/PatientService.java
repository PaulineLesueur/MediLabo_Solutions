package com.openclassrooms.ms_patient.service;

import com.openclassrooms.ms_patient.exceptions.DuplicatePatientException;
import com.openclassrooms.ms_patient.exceptions.ResourceNotFoundException;
import com.openclassrooms.ms_patient.model.Patient;
import com.openclassrooms.ms_patient.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class PatientService {

    private static final Logger logger = LogManager.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    public Page<Patient> findAll(int page, int size) {
        logger.info("Fetching patients - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable);
    }

    public Patient findById(Integer id) {
        logger.info("Finding patient with ID: {}", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient with ID {} not found", id);
                    return new ResourceNotFoundException("Patient with ID " + id + " not found");
                });
    }

    public Patient createPatient(Patient newPatient) {
        logger.info("Creating new patient: {}", newPatient);
        if(patientRepository.findByFirstNameAndLastName(newPatient.getFirstName(), newPatient.getLastName()).isPresent()) {
            logger.warn("Duplicate patient attempt with name: {} {}", newPatient.getFirstName(), newPatient.getLastName());
            throw new DuplicatePatientException("There already is a patient with this first and last name");
        }

        Patient savedPatient = patientRepository.save(newPatient);
        logger.info("Sucessfully created patient with name {} {}", savedPatient.getFirstName(), savedPatient.getLastName());
        return savedPatient;
    }

    public Patient updatePatient(Integer id, Patient updatedPatient) {
        Patient patientToUpdate = findById(id);
        logger.info("Updating patient with ID: {}", id);

        updateIfNotNull(updatedPatient.getLastName(), patientToUpdate::setLastName);
        updateIfNotNull(updatedPatient.getFirstName(), patientToUpdate::setFirstName);
        updateIfNotNull(updatedPatient.getBirthdate(), patientToUpdate::setBirthdate);
        updateIfNotNull(updatedPatient.getGender(), patientToUpdate::setGender);
        updateIfNotNull(updatedPatient.getAddress(), patientToUpdate::setAddress);
        updateIfNotNull(updatedPatient.getPhone(), patientToUpdate::setPhone);

        Patient updatedPatientEntity = patientRepository.save(patientToUpdate);
        logger.info("Successfully updated patient with ID: {}", updatedPatientEntity.getId());
        return updatedPatientEntity;
    }

    private <T> void updateIfNotNull(T value, Consumer<T> updater) {
        if (value != null) {
            updater.accept(value);
        }
    }
}
