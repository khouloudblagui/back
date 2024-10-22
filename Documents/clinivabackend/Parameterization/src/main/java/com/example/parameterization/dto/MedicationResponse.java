package com.example.parameterization.dto;

import com.example.parameterization.Enum.DosageForm;
import com.example.parameterization.Enum.MedicationStrength;
import com.example.parameterization.Enum.MedicationType;
import lombok.Data;

import java.util.List;

@Data
public class MedicationResponse {
    private Integer medicationKy;
    private String medicationCode;
    private String medicationName;
    private MedicationType medicationType;
    private MedicationStrength medicationStrength;
    private DosageForm medicationDosageForm;
    private List<IngredientResponse> ingredients;
}
