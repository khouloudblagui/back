package org.example.doctor.Service;

import org.example.doctor.Entity.Patient;
import org.example.doctor.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Créer un nouveau patient
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Trouver un patient par son ID
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
    }

    // Trouver un patient par son email
    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    // Mettre à jour un patient existant
    public Patient updatePatient(Long patientId, Patient updatedPatient) {
        Patient existingPatient = getPatientById(patientId);
        existingPatient.setFirstName(updatedPatient.getFirstName());
        existingPatient.setLastName(updatedPatient.getLastName());
        existingPatient.setEmail(updatedPatient.getEmail());
        // Mettre à jour d'autres informations si nécessaire
        return patientRepository.save(existingPatient);
    }

    // Supprimer un patient par son ID
    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }

    // Récupérer tous les patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
