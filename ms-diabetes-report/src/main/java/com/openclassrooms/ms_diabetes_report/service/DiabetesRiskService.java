package com.openclassrooms.ms_diabetes_report.service;

import com.openclassrooms.ms_diabetes_report.DTO.NoteDTO;
import com.openclassrooms.ms_diabetes_report.enums.RiskLevel;
import com.openclassrooms.ms_diabetes_report.enums.Symptoms;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiabetesRiskService {
    public RiskLevel calculateRisk(int age, String gender, List<NoteDTO> patientNotes) {
        Set<String> normalizedNotes = patientNotes.stream()
                .map(s -> s.getNote().toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        Set<String> foundSymptoms = Symptoms.getAllKeywords().stream()
                .map(String::toLowerCase)
                .filter(keyword -> normalizedNotes.stream().anyMatch(note -> note.contains(keyword)))
                .collect(Collectors.toSet());

        int symptomCount = foundSymptoms.size();

        if(symptomCount == 0) {

            return RiskLevel.NONE;

        } else if(symptomCount >= 2 && symptomCount <= 5 && age > 30) {

            return RiskLevel.BORDERLINE;

        } else if(
                (gender.equalsIgnoreCase("M") && age < 30 && symptomCount >= 3 && symptomCount < 5) ||
                (gender.equalsIgnoreCase("M") && age >= 30 && symptomCount >= 6 && symptomCount < 8) ||
                (gender.equalsIgnoreCase("F") && age < 30 && symptomCount >= 4 && symptomCount < 7) ||
                (gender.equalsIgnoreCase("F") && age >= 30 && symptomCount == 7)) {

            return RiskLevel.IN_DANGER;

        } else if(
                (gender.equalsIgnoreCase("M") && age < 30 && symptomCount >= 5) ||
                (gender.equalsIgnoreCase("F") && age < 30 && symptomCount >= 7) ||
                age > 30 && symptomCount >= 8
        ) {

            return RiskLevel.EARLY_ONSET;

        } else {

            return RiskLevel.NONE;

        }
    }
}
