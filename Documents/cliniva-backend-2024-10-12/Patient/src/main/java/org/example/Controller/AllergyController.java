package org.example.Controller;

import org.example.DTO.AllergyDTO;
import org.example.Service.AllergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class AllergyController {

    @Autowired
    private AllergyService allergyService;

    @GetMapping("/{patientId}/allergies")
    public ResponseEntity<List<AllergyDTO>> getAllergiesByPatientId(@PathVariable Long patientId) {
        List<AllergyDTO> allergies = allergyService.getAllergiesByPatientId(patientId);
        return ResponseEntity.ok(allergies);
    }

    @GetMapping("/allergies/{allergyId}")
    public ResponseEntity<AllergyDTO> getAllergyById(@PathVariable Long allergyId) {
        AllergyDTO allergy = allergyService.getAllergyById(allergyId);
        return ResponseEntity.ok(allergy);
    }
}
