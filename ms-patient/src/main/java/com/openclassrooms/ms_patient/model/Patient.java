package com.openclassrooms.ms_patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Lastname is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Firstname is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Birthdate is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String address;
    private String phone;
}
