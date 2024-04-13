package com.example.parameterization.Entity;

import com.example.parameterization.Enum.Severity;
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
public class AdverseEffect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idAdverseEffect;

    private String adverseEffectName;

    private String adverseEffectDesc;

    @Enumerated(EnumType.ORDINAL)
    private Severity adverseEffectSeverity;

    @ManyToMany(mappedBy = "sideEffects", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("sideEffects")
    private Set<Vaccination> vaccinations = new HashSet<>();


}

