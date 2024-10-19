package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDTO {
    private Integer medicationKy;
    private String medicationCode;
    private String medicationName;
    private String medicationType;
    private String medicationStrength;
    private String medicationDosageForm;
}
