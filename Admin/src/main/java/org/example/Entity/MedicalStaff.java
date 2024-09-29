package org.example.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "MedicalStaff")
public class MedicalStaff extends User {

    @Column(name = "specialization")
    private String specialization; // Exemple : Cardiologie, Chirurgie

    @Column(name = "department")
    private String department; // Département auquel le personnel appartient

    // Possibilité d'ajouter des relations si besoin
}
