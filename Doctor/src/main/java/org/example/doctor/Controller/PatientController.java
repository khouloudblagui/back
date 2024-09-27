package org.example.doctor.Controller;

import org.example.doctor.Entity.Patient;
import org.example.doctor.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Créer un nouveau patient
    @PostMapping("/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient newPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    // Récupérer un patient par son ID
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long patientId) {
        Patient patient = patientService.getPatientById(patientId);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    // Récupérer un patient par son email
    @GetMapping("/email")
    public ResponseEntity<Optional<Patient>> getPatientByEmail(@RequestParam String email) {
        Optional<Patient> patient = patientService.getPatientByEmail(email);
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
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Récupérer tous les patients
    @GetMapping("/")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}
