package org.example.Repository;


import org.example.Entity.HealthMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthMonitoringRepository extends JpaRepository<HealthMonitoring, Long> {
    List<HealthMonitoring> findByPatientId(Long patientId); // Récupérer toutes les entrées pour un patient
}
