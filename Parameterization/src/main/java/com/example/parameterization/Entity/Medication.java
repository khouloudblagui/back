package com.example.parameterization.Entity;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Medication {

    @Id
    private String Medication_Code;
    private String Medication_Name;
    private MedicationType Medication_Type;
    private List<Ingredient> Medication_ActiveIngredients;
    private Object  Medication_Strength;
    private DosageForm Medication_DosageForm;

    public enum MedicationType {
        OINTMENT,
        SOFT_CAPSULE,
        FILM_COATED_TABLET
    }
    public enum StrengthTablet {
        STRENGTH_50MG,
        STRENGTH_100MG,
        STRENGTH_250MG,
        STRENGTH_500MG,
        STRENGTH_1000MG
    }

    public enum StrengthLiquid {
        STRENGTH_5MG_PER_ML,
        STRENGTH_10MG_PER_ML,
        STRENGTH_20MG_PER_ML,
        STRENGTH_50MG_PER_ML,
        STRENGTH_100MG_PER_ML
    }

    public enum StrengthCream {
        STRENGTH_1_PERCENT,
        STRENGTH_2_PERCENT,
        STRENGTH_5_PERCENT,
        STRENGTH_10_PERCENT
    }
    public enum DosageForm {
        ORAL,
        TOPICAL,
        INJECTABLE
    }

    public Medication(String medication_Code, String medication_Name, MedicationType medication_Type, List<Ingredient> medication_ActiveIngredients, Object medication_Strength, DosageForm medication_DosageForm) {
        Medication_Code = medication_Code;
        Medication_Name = medication_Name;
        Medication_Type = medication_Type;
        Medication_ActiveIngredients = medication_ActiveIngredients;
        Medication_Strength = medication_Strength;
        Medication_DosageForm = medication_DosageForm;
    }

    public String getMedication_Code() {
        return Medication_Code;
    }

    public void setMedication_Code(String medication_Code) {
        Medication_Code = medication_Code;
    }

    public String getMedication_Name() {
        return Medication_Name;
    }

    public void setMedication_Name(String medication_Name) {
        Medication_Name = medication_Name;
    }

    public MedicationType getMedication_Type() {
        return Medication_Type;
    }

    public void setMedication_Type(MedicationType medication_Type) {
        Medication_Type = medication_Type;
    }

    public List<Ingredient> getMedication_ActiveIngredients() {
        return Medication_ActiveIngredients;
    }

    public void setMedication_ActiveIngredients(List<Ingredient> medication_ActiveIngredients) {
        Medication_ActiveIngredients = medication_ActiveIngredients;
    }

    public Object getMedication_Strength() {
        return Medication_Strength;
    }

    public void setMedication_Strength(Object medication_Strength) {
        Medication_Strength = medication_Strength;
    }

    public DosageForm getMedication_DosageForm() {
        return Medication_DosageForm;
    }

    public void setMedication_DosageForm(DosageForm medication_DosageForm) {
        Medication_DosageForm = medication_DosageForm;
    }

}
