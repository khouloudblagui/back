package org.example.Controller;

import org.example.Entity.HealthMonitoring;
import org.example.Service.HealthMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/health-monitoring")
public class HealthMonitoringController {

    @Autowired
    private HealthMonitoringService healthMonitoringService;

    // Récupérer toutes les données de suivi pour un patient donné
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<HealthMonitoring>> getHealthMonitoringByPatientId(@PathVariable Long patientId) {
        List<HealthMonitoring> healthMonitoringList = healthMonitoringService.getHealthMonitoringByPatientId(patientId);
        return ResponseEntity.ok(healthMonitoringList);
    }

    // Créer ou mettre à jour une entrée de suivi de santé
    @PostMapping
    public ResponseEntity<HealthMonitoring> saveHealthMonitoring(@RequestBody HealthMonitoring healthMonitoring) {
        HealthMonitoring savedHealthMonitoring = healthMonitoringService.saveHealthMonitoring(healthMonitoring);
        return ResponseEntity.ok(savedHealthMonitoring);
    }

    // Supprimer une entrée de suivi de santé par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthMonitoring(@PathVariable Long id) {
        healthMonitoringService.deleteHealthMonitoring(id);
        return ResponseEntity.noContent().build();
    }

    // Récupérer une entrée de suivi de santé par ID
    @GetMapping("/{id}")
    public ResponseEntity<HealthMonitoring> getHealthMonitoringById(@PathVariable Long id) {
        HealthMonitoring healthMonitoring = healthMonitoringService.getHealthMonitoringById(id);
        return ResponseEntity.ok(healthMonitoring);
    }
}
