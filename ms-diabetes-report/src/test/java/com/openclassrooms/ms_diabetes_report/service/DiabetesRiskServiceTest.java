package com.openclassrooms.ms_diabetes_report.service;

import com.openclassrooms.ms_diabetes_report.DTO.NoteDTO;
import com.openclassrooms.ms_diabetes_report.enums.RiskLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(DiabetesRiskService.class)
public class DiabetesRiskServiceTest {
    private final DiabetesRiskService diabetesRiskService = new DiabetesRiskService();

    @Test
    public void testCalculateRisk_ReturnsNone() {
        int age = 35;
        String gender = "M";
        List<NoteDTO> notes = List.of(new NoteDTO("1", 3, "TestNone", "No symptoms"));

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.NONE);
    }

    @Test
    public void testCalculateRisk_ReturnsBorderline() {
        int age = 35;
        String gender = "M";
        List<NoteDTO> notes = List.of(new NoteDTO("1", 3, "TestBorderline", "Fumeur, taille"));

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.BORDERLINE);
    }

    @Test
    public void testCalculateRisk_MaleUnderThirtyYearsOld_ReturnsInDanger() {
        int age = 25;
        String gender = "M";
        List<NoteDTO> notes = List.of(
                new NoteDTO("1", 3, "TestInDanger", "Fumeur, taille, anticorps")
        );

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.IN_DANGER);
    }

    @Test
    public void testCalculateRisk_MaleOverThirtyYearsOld_ReturnsInDanger() {
        int age = 35;
        String gender = "M";
        List<NoteDTO> notes = List.of(
                new NoteDTO("1", 3, "TestInDanger", "Fumeur, taille, anticorps, cholestérol, poids, réaction")
        );

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.IN_DANGER);
    }

    @Test
    public void testCalculateRisk_FemaleUnderThirtyYearsOld_ReturnsInDanger() {
        int age = 25;
        String gender = "F";
        List<NoteDTO> notes = List.of(
                new NoteDTO("1", 3, "TestInDanger", "Fumeuse, taille, anticorps, cholestérol")
        );

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.IN_DANGER);
    }

    @Test
    public void testCalculateRisk_FemaleOverThirtyYearsOld_ReturnsInDanger() {
        int age = 35;
        String gender = "F";
        List<NoteDTO> notes = List.of(
                new NoteDTO("1", 3, "TestInDanger", "Fumeuse, taille, anticorps, cholestérol, poids , anormal, microalbumine")
        );

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.IN_DANGER);
    }

    @Test
    public void testCalculateRisk_MaleUnderThirtyYearsOld_ReturnsEarlyOnset() {
        int age = 25;
        String gender = "M";
        List<NoteDTO> notes = List.of(
                new NoteDTO("1", 3, "TestEarlyOnset", "Fumeuse, taille, anticorps, cholestérol, poids")
        );

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.EARLY_ONSET);
    }

    @Test
    public void testCalculateRisk_FemaleUnderThirtyYearsOld_ReturnsEarlyOnset() {
        int age = 25;
        String gender = "F";
        List<NoteDTO> notes = List.of(
                new NoteDTO("1", 3, "TestEarlyOnset", "Fumeuse, taille, anticorps, cholestérol, poids, anormal, microalbumine")
        );

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.EARLY_ONSET);
    }

    @Test
    public void testCalculateRisk_ReturnsEarlyOnset() {
        int age = 35;
        String gender = "M";
        List<NoteDTO> notes = List.of(
                new NoteDTO("1", 3, "TestEarlyOnset", "Fumeuse, taille, anticorps, cholestérol, poids, anormal, microalbumine, rechute")
        );

        RiskLevel result = diabetesRiskService.calculateRisk(age, gender, notes);

        assertThat(result).isEqualTo(RiskLevel.EARLY_ONSET);
    }
}
