package com.example.parameterization.Entity;

import com.example.parameterization.Enum.AllergyType;
import com.example.parameterization.Enum.Severity;
import com.example.parameterization.Enum.Symptoms;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Enumerated(EnumType.ORDINAL)
    private Symptoms allergySymptoms;

}
