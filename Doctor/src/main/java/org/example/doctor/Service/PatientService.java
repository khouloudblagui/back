package org.example.doctor.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.authentification.dto.UserDto;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Exception.PatientNotFoundException;
import org.example.doctor.Feign.UserClient;
import org.example.doctor.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserClient userClient;

    @Autowired
    public PatientService(PatientRepository patientRepository, UserClient userClient) {
        this.patientRepository = patientRepository;
        this.userClient = userClient;
    }

    // Créer un nouveau patient
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Trouver un patient par son ID et enrichir les informations avec celles de l'utilisateur
    public Patient getPatientById(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

        // Utiliser Feign pour obtenir les détails de l'utilisateur
        UserDto user = userClient.getUserById(patient.getUserId());
        patient.setName(user.getFirstname() + " " + user.getLastname());
        patient.setEmail(user.getEmail());
        patient.setMobile(user.getPhone());

        return patient;
    }

    // Mettre à jour un patient existant
    public Patient updatePatient(Long patientId, Patient updatedPatient) {
        Patient existingPatient = getPatientById(patientId);
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setBGroup(updatedPatient.getBGroup());
        existingPatient.setDate(updatedPatient.getDate());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setTreatment(updatedPatient.getTreatment());
        // Ne pas mettre à jour les informations utilisateur ici, car elles sont gérées dans le microservice Authentification
        return patientRepository.save(existingPatient);
    }

    // Supprimer un patient par son ID
    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }

    // Récupérer tous les patients
    public List<Patient> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        patients.forEach(patient -> {
            UserDto user = userClient.getUserById(patient.getUserId());
            patient.setName(user.getFirstname() + " " + user.getLastname());
            patient.setEmail(user.getEmail());
            patient.setMobile(user.getPhone());
        });
        return patients;
    }
}