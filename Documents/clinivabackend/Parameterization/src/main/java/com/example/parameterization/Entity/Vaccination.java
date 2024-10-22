package com.example.parameterization.Entity;

import com.example.parameterization.Enum.VaccineType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVaccination;

    private String vaccineLabel;

    @Enumerated(EnumType.ORDINAL)
    private VaccineType vaccineType;

    @ManyToOne
    @JoinColumn(name = "idICD10")
    private ICD10 vaccineICD10Code;

    @ManyToOne
    @JoinColumn(name = "idMedication")
    private Medication vaccineMedication;

    private String vaccineManufacturer;

    //FetchType.LAZY
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("vaccinations")
    @JoinTable(name = "vaccination_effects",
            joinColumns = {
                    @JoinColumn(name = "idVaccination", referencedColumnName = "idVaccination")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "idAdverseEffect", referencedColumnName = "idAdverseEffect")
            })
    private Set<AdverseEffect> sideEffects = new HashSet<>();


}
