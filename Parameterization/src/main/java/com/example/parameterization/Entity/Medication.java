package com.example.parameterization.Entity;
import com.example.parameterization.Enum.DosageForm;
import com.example.parameterization.Enum.MedicationStrength;
import com.example.parameterization.Enum.MedicationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_ky", unique = true)
    private Integer medicationKy;

    @Column(name = "medication_code", nullable = false)
    private String medicationCode;

    @Column(name = "medication_name")
    private String medicationName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "medication_type")
    private MedicationType medicationType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "medication_strength")
    private MedicationStrength medicationStrength;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "medication_dosage_form")
    private DosageForm medicationDosageForm;

    @OneToMany(mappedBy = "med")
    private List<MedicIngredientLink> medicIngredientLinks;



}
