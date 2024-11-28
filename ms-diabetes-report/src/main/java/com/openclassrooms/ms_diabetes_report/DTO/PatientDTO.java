package com.openclassrooms.ms_diabetes_report.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private long id;
    private String lastName;
    private String firstName;
    private LocalDate birthdate;
    private String gender;
    private String address;
    private String phone;
}
