package org.example.Controller;

import org.example.DTO.PatientDTO;
import org.example.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctorPatientController")
public class PatienttController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        // Récupère les données du patient via le service et les renvoie en réponse
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }
}
