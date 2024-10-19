package org.example.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Enum.IndicatorType;
import org.example.doctor.Entity.Patient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "health_monitoring")
public class HealthMonitoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;  // Hérité de Doctor

    @Enumerated(EnumType.STRING)  // Utilisation d'un enum pour les types d'indicateur
    @Column(name = "indicator_type", nullable = false)
    private IndicatorType indicatorType;  // Type de l'indicateur (ex: poids, température)

    @Column(name = "value", nullable = false)
    private BigDecimal value;  // Utiliser BigDecimal pour les valeurs numériques précises

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;
}
