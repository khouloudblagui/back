package org.example.Controller;


import org.example.DTO.MedicationDTO;
import org.example.Service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patient/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @GetMapping
    public ResponseEntity<List<MedicationDTO>> getAllMedications() {
        List<MedicationDTO> medications = medicationService.getAllMedications();
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/{medicationId}")
    public ResponseEntity<MedicationDTO> getMedicationById(@PathVariable Integer medicationId) {
        MedicationDTO medication = medicationService.getMedicationById(medicationId);
        return ResponseEntity.ok(medication);
    }
}
