package org.example.Service;

import org.example.Entity.HealthMonitoring;
import org.example.Repository.HealthMonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthMonitoringService {

    @Autowired
    private HealthMonitoringRepository healthMonitoringRepository;

    // Récupérer toutes les données de suivi pour un patient donné
    public List<HealthMonitoring> getHealthMonitoringByPatientId(Long patientId) {
        return healthMonitoringRepository.findByPatientId(patientId);
    }

    // Créer ou mettre à jour une entrée de suivi de santé
    public HealthMonitoring saveHealthMonitoring(HealthMonitoring healthMonitoring) {
        return healthMonitoringRepository.save(healthMonitoring);
    }

    // Supprimer une entrée de suivi de santé par ID
    public void deleteHealthMonitoring(Long id) {
        healthMonitoringRepository.deleteById(id);
    }

    // Récupérer une entrée de suivi de santé par ID
    public HealthMonitoring getHealthMonitoringById(Long id) {
        return healthMonitoringRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Health Monitoring entry not found with ID: " + id));
    }
}
