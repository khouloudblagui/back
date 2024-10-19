package org.example.Controller;

import org.example.Entity.MedicalRecord;
import org.example.Service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // Récupérer un dossier médical par ID de patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByPatientId(@PathVariable Long patientId) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByPatientId(patientId);
        return ResponseEntity.ok(medicalRecord);
    }

    // Créer ou mettre à jour un dossier médical
    @PostMapping
    public ResponseEntity<MedicalRecord> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord savedRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
        return ResponseEntity.ok(savedRecord);
    }

    // Supprimer un dossier médical par ID
    @DeleteMapping("/{medicalRecordId}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long medicalRecordId) {
        medicalRecordService.deleteMedicalRecord(medicalRecordId);
        return ResponseEntity.noContent().build();
    }
}
