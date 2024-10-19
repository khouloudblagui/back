package org.example.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "OperationRoom")
public class OperationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long id;

    @Column(name = "patient_name")
    private String patientName; // Correspond à pName

    @Column(name = "roomNumber", nullable = false, unique = true)
    private String roomNumber; // Correspond à rNo

    @Column(name = "roomType")
    private String roomType; // Correspond à rType

    @Column(name = "gender")
    private String gender;

    @Column(name = "admitDate")
    private LocalDate admitDate = LocalDate.now(); // Correspond à admitDate

    @Column(name = "dischargeDate")
    private LocalDate dischargeDate = LocalDate.now(); // Correspond à dischargeDate

    @Column(name = "status")
    private String status; // Exemple : Disponible, Occupée, En maintenance

    @OneToMany(mappedBy = "operationRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Surgery> surgeries = new HashSet<>();
}
