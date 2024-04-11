package com.example.parameterization.Entity;
import java.io.Serializable;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ICD10 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name ="icd10Ky")
    private Integer ICD10ky;
    @NonNull
    @Column(name ="icd10Code")
    private String ICD10Code;
    @Column(name ="icd10Description")
    private String ICD10Description;
    @Column(name ="icd10Chapter")
    private String ICD10Chapter;
    @Column(name ="icd10Block")
    private String ICD10Block;
    @Column(name ="icd10Category")
    private String ICD10Category;
    @Column(name ="icd10Subcategory")
    private String ICD10Subcategory;
    @Column(name ="icd10Extension")
    private String ICD10Extension ;
    @Column(name ="icd10Notes")
    private String ICD10Notes ;
}
