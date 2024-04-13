package com.example.parameterization.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Symptoms {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long symptomKy;

    private String symptomName;
    private String symptomDesc;

    @ManyToMany(mappedBy = "symptoms", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("symptoms")
    private Set<Allergy> allergies = new HashSet<>();

    // Constructors
    public Symptoms() {
    }

    public Symptoms(String symptomName, String symptomDesc, Set<Allergy> allergies) {
        this.symptomName = symptomName;
        this.symptomDesc = symptomDesc;
        this.allergies = allergies;
    }

    // Getters and Setters
    public Long getSymptomKy() {
        return symptomKy;
    }

    public void setSymptomKy(Long symptomKy) {
        this.symptomKy = symptomKy;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getSymptomDesc() {
        return symptomDesc;
    }

    public void setSymptomDesc(String symptomDesc) {
        this.symptomDesc = symptomDesc;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }
}
