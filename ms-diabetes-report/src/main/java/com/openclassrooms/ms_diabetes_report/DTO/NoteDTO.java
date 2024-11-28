package com.openclassrooms.ms_diabetes_report.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private String id;
    private int patId;
    private String patient;
    private String note;
}
