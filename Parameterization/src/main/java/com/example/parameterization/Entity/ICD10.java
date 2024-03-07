package com.example.parameterization.Entity;
import java.io.Serializable;
import java.math.BigInteger;

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
    @Column(unique = true,name ="ICD10_Ky")
    private Integer ICD10ky;
    @NonNull
    @Column(name ="ICD10_Code")
    private String ICD10Code;
    @Column(name ="ICD10_Description")
    private String ICD10Description;
    @Column(name ="ICD10_Chapter")
    private String ICD10Chapter;
    @Column(name ="ICD10_Block")
    private String ICD10Block;
    @Column(name ="ICD10_Category")
    private String ICD10Category;
    @Column(name ="ICD10_Subcategory")
    private String ICD10Subcategory;
    @Column(name ="ICD10_Extension")
    private String ICD10Extension ;
    @Column(name ="ICD10_Notes")
    private String ICD10Notes ;
}
