package org.example.doctor.Entity;

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
@Table(name = "Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private Long id;

    private Long userId; // Référence à l'utilisateur dans le microservice Authentification

    @Transient
    private String name; // Récupéré via Authentification, non persisté

    @Column(name = "email", unique = true)
    private String email;

    @Transient
    private String mobile;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "bGroup")
    private String bGroup;

    @Column(name = "date")
    private String date;

    @Column(name = "address")
    private String address;

    @Column(name = "treatment")
    private String treatment;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();
}
