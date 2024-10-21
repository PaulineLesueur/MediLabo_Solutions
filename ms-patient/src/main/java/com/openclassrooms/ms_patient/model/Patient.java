package com.openclassrooms.ms_patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String lastName;
    private String firstName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private String gender;
    private String adress;
    private String phone;
}
