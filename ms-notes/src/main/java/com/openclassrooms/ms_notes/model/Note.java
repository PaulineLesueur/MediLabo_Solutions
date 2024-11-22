package com.openclassrooms.ms_notes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Notes")
public class Note {
    @Id
    private String id;
    private Integer patId;
    private String patient;
    private String note;
}
