package org.example.doctor.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Exception.PatientNotFoundException;
import org.example.doctor.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Créer un nouveau patient
    @PostMapping
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient newPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    // Récupérer un patient par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    // Mettre à jour un patient existant
    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId,
                                                 @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(patientId, updatedPatient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    // Supprimer un patient
    @DeleteMapping("/{patientId}")
   // @PreAuthorize("hasRole('ADMIN')") // Seuls les admins peuvent supprimer un patient
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Récupérer tous les patients
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}