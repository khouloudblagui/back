package com.example.parameterization.mapper;


import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.dto.IngredientResponse;
import com.example.parameterization.dto.MedicationResponse;

import java.util.ArrayList;
import java.util.List;

public class MedicationMapper {

    public static MedicationResponse toMedicationResponse(Medication medication) {
        MedicationResponse medicationResponse = new MedicationResponse();
        medicationResponse.setMedicationKy(medication.getMedicationKy());
        medicationResponse.setMedicationCode(medication.getMedicationCode());
        medicationResponse.setMedicationName(medication.getMedicationName());
        medicationResponse.setMedicationStrength(medication.getMedicationStrength());
        medicationResponse.setMedicationDosageForm(medication.getMedicationDosageForm());
        medicationResponse.setMedicationType(medication.getMedicationType());

        List<IngredientResponse> ingredientResponses = new ArrayList<>();
        medication.getMedicIngredientLinks().forEach(medicIngredientLink -> {
            if (medicIngredientLink.getIng() != null) {
                IngredientResponse ingredientResponse = new IngredientResponse();
                Ingredient ingredient = medicIngredientLink.getIng();
                ingredientResponse.setIngredientName(ingredient.getIngredientName());
                ingredientResponse.setIngredientDesc(ingredient.getIngredientDesc());
                ingredientResponses.add(ingredientResponse);
            }
        });
        medicationResponse.setIngredients(ingredientResponses);

        return medicationResponse;
    }
}
