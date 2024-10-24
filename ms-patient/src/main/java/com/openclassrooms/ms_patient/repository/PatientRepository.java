package com.openclassrooms.ms_patient.repository;

import com.openclassrooms.ms_patient.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
