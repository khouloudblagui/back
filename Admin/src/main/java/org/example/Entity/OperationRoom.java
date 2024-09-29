package org.example.Entity;

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
@Table(name = "OperationRoom")
public class OperationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long id;

    @Column(name = "roomNumber", nullable = false, unique = true)
    private String roomNumber;

    @Column(name = "status")
    private String status; // Exemple : Disponible, Occupée, En maintenance

    @OneToMany(mappedBy = "operationRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Surgery> surgeries = new HashSet<>();
}
