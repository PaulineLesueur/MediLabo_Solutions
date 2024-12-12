package com.openclassrooms.ms_diabetes_report.controller;

import com.openclassrooms.ms_diabetes_report.DTO.NoteDTO;
import com.openclassrooms.ms_diabetes_report.DTO.PatientDTO;
import com.openclassrooms.ms_diabetes_report.client.NoteClient;
import com.openclassrooms.ms_diabetes_report.client.PatientClient;
import com.openclassrooms.ms_diabetes_report.enums.RiskLevel;
import com.openclassrooms.ms_diabetes_report.service.DiabetesRiskService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class DiabetesReportController {

    private final DiabetesRiskService riskService;
    private final PatientClient patientClient;
    private final NoteClient noteClient;

    public DiabetesReportController(DiabetesRiskService riskService, PatientClient patientClient, NoteClient noteClient) {
        this.riskService = riskService;
        this.patientClient = patientClient;
        this.noteClient = noteClient;
    }

    @GetMapping("/diabetes-report/{patId}")
    public RiskLevel getDiabetesRisk(@PathVariable int patId) {
        PatientDTO patient = patientClient.getPatientById(patId);
        List<NoteDTO> notes = noteClient.getNotesByPatientId(patId);
        int age = calculateAge(patient.getBirthdate());

        return riskService.calculateRisk(age, patient.getGender(), notes);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
