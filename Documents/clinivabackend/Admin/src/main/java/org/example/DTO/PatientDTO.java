package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private Long id;
    private String name;
    private String medicalRecordNumber;
}
