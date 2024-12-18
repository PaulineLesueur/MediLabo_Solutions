package com.openclassrooms.ms_diabetes_report.service;

import com.openclassrooms.ms_diabetes_report.DTO.NoteDTO;
import com.openclassrooms.ms_diabetes_report.enums.RiskLevel;
import com.openclassrooms.ms_diabetes_report.enums.Symptoms;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiabetesRiskService {
    private static final Logger logger = LogManager.getLogger(DiabetesRiskService.class);

    public RiskLevel calculateRisk(int age, String gender, List<NoteDTO> patientNotes) {
        logger.info("Starting risk calculation for patient: age={}, gender={}, notesCount={}", age, gender, patientNotes.size());

        Set<String> normalizedNotes = patientNotes.stream()
                .map(s -> s.getNote().toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
        logger.debug("Normalized notes: {}", normalizedNotes);

        Set<String> foundSymptoms = Symptoms.getAllKeywords().stream()
                .map(String::toLowerCase)
                .filter(keyword -> normalizedNotes.stream().anyMatch(note -> note.contains(keyword)))
                .collect(Collectors.toSet());

        int symptomCount = foundSymptoms.size();
        logger.info("Symptoms detected: {}, total count: {}", foundSymptoms, symptomCount);

        RiskLevel riskLevel;
        if(symptomCount == 0) {

            riskLevel = RiskLevel.NONE;

        } else if(symptomCount >= 2 && symptomCount <= 5 && age > 30) {

            riskLevel = RiskLevel.BORDERLINE;

        } else if(
                (gender.equalsIgnoreCase("M") && age < 30 && symptomCount >= 3 && symptomCount < 5) ||
                (gender.equalsIgnoreCase("M") && age >= 30 && symptomCount >= 6 && symptomCount < 8) ||
                (gender.equalsIgnoreCase("F") && age < 30 && symptomCount >= 4 && symptomCount < 7) ||
                (gender.equalsIgnoreCase("F") && age >= 30 && symptomCount == 7)) {

            riskLevel = RiskLevel.IN_DANGER;

        } else if(
                (gender.equalsIgnoreCase("M") && age < 30 && symptomCount >= 5) ||
                (gender.equalsIgnoreCase("F") && age < 30 && symptomCount >= 7) ||
                age > 30 && symptomCount >= 8
        ) {

            riskLevel = RiskLevel.EARLY_ONSET;

        } else {

            riskLevel = RiskLevel.NONE;

        }

        logger.info("Risk level calculated: {}", riskLevel);
        return riskLevel;
    }
}
