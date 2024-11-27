package com.openclassrooms.ms_diabetes_report.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
    private long id;
    private String lastName;
    private String firstName;
    private LocalDate birthdate;
    private String gender;
    private String address;
    private String phone;
}
