package org.example.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.doctor.Entity.Patient;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;  // Hérité de Doctor

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Column(name = "current_conditions", columnDefinition = "TEXT")
    private String currentConditions;

    // IDs des allergies, à récupérer dynamiquement depuis le microservice Parameterization
    @ElementCollection
    @CollectionTable(name = "medical_record_allergies", joinColumns = @JoinColumn(name = "medical_record_id"))
    @Column(name = "allergyKy")  // Le nom correct de l'ID des allergies
    private List<Long> allergyIds;

    // IDs des médicaments, à récupérer dynamiquement depuis le microservice Parameterization
    @ElementCollection
    @CollectionTable(name = "medical_record_medications", joinColumns = @JoinColumn(name = "medical_record_id"))
    @Column(name = "medicationKy")  // Le nom correct de l'ID des médicaments
    private List<Long> medicationIds;
}
