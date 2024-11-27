package com.openclassrooms.ms_diabetes_report.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDTO {
    private String id;
    private int patId;
    private String patient;
    private String note;
}
