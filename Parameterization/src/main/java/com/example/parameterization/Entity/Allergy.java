package com.example.parameterization.Entity;

import com.example.parameterization.Enum.AllergyType;
import com.example.parameterization.Enum.Severity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true )
    private Long allergyKy;

    private String allergyName;

    @Enumerated(EnumType.ORDINAL)
    private AllergyType allergyType;

    private String allergyDesc;

    @Enumerated(EnumType.ORDINAL)
    private Severity allergySeverity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("allergies")
    @JoinTable(name = "AllergySymptomsLink",
            joinColumns = {
                    @JoinColumn(name = "allergyKy", referencedColumnName = "allergyKy")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "symptomKy", referencedColumnName = "symptomKy")
            })
    private Set<Symptoms> symptoms = new HashSet<>();

    // Constructors
    public Allergy() {
    }

    public Allergy(String allergyName, AllergyType allergyType, String allergyDesc, Severity allergySeverity, Set<Symptoms> symptoms) {
        this.allergyName = allergyName;
        this.allergyType = allergyType;
        this.allergyDesc = allergyDesc;
        this.allergySeverity = allergySeverity;
        this.symptoms = symptoms;
    }

    // Getters and Setters
    public Long getAllergyKy() {
        return allergyKy;
    }

    public void setAllergyKy(Long allergyKy) {
        this.allergyKy = allergyKy;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public AllergyType getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(AllergyType allergyType) {
        this.allergyType = allergyType;
    }

    public String getAllergyDesc() {
        return allergyDesc;
    }

    public void setAllergyDesc(String allergyDesc) {
        this.allergyDesc = allergyDesc;
    }

    public Severity getAllergySeverity() {
        return allergySeverity;
    }

    public void setAllergySeverity(Severity allergySeverity) {
        this.allergySeverity = allergySeverity;
    }

    public Set<Symptoms> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<Symptoms> symptoms) {
        this.symptoms = symptoms;
    }

/*
@Entity
public class Allergy implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "allergy_name")
    private String allergyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "allergy_type")
    private AllergyType allergyType;

    @Column(name = "allergy_desc", columnDefinition = "TEXT")
    private String allergyDesc;

    @ElementCollection(targetClass = Symptoms.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "allergy_symptoms", joinColumns = @JoinColumn(name = "allergy_id"))
    @Column(name = "symptom")
    private List<Symptoms> allergySymptoms;

    @Enumerated(EnumType.STRING)
    @Column(name = "allergy_severity")
    private Severity allergySeverity;

    // Constructors
    public Allergy() {
    }

    public Allergy(String allergyName, AllergyType allergyType, String allergyDesc, List<Symptoms> allergySymptoms, Severity allergySeverity) {
        this.allergyName = allergyName;
        this.allergyType = allergyType;
        this.allergyDesc = allergyDesc;
        this.allergySymptoms = allergySymptoms;
        this.allergySeverity = allergySeverity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public AllergyType getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(AllergyType allergyType) {
        this.allergyType = allergyType;
    }

    public String getAllergyDesc() {
        return allergyDesc;
    }

    public void setAllergyDesc(String allergyDesc) {
        this.allergyDesc = allergyDesc;
    }

    public List<Symptoms> getAllergySymptoms() {
        return allergySymptoms;
    }

    public void setAllergySymptoms(List<Symptoms> allergySymptoms) {
        this.allergySymptoms = allergySymptoms;
    }

    public Severity getAllergySeverity() {
        return allergySeverity;
    }

    public void setAllergySeverity(Severity allergySeverity) {
        this.allergySeverity = allergySeverity;
    }
}*/


}
