package com.openclassrooms.ms_patient.service;

import com.openclassrooms.ms_patient.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PatientService {
    public static List<Patient> allPatients = new ArrayList<>();
    static {
        allPatients.add(new Patient(1L, "TestNone", "Test", new Date(), "F", "1 Brookside St", "100-222-3333"));
        allPatients.add(new Patient(2L, "TestBorderline", "Test", new Date(), "M", "2 High St", "200-333-4444"));
        allPatients.add(new Patient(3L, "TestDanger", "Test", new Date(), "M", "3 Club Road", "300-444-5555"));
        allPatients.add(new Patient(4L, "TestEarlyOnset", "Test", new Date(), "F", "4 Valley Dr", "400-555-6666"));
    }

    public List<Patient> getAllPatients() {
        return  allPatients;
    }
}
