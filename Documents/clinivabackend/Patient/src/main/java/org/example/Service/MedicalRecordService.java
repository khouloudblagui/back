package org.example.Service;

import org.example.DTO.AllergyDTO;
import org.example.DTO.MedicationDTO;
import org.example.Entity.MedicalRecord;
import org.example.Feign.ParameterizationClient;
import org.example.Repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private ParameterizationClient parameterizationClient;

    public MedicalRecord getMedicalRecordByPatientId(Long patientId) {
        // Vérifier si le dossier médical existe
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatientId(patientId);
        if (medicalRecord == null) {
            throw new RuntimeException("Medical record not found for patient ID: " + patientId);
        }

        try {
            // Récupérer les médicaments via Feign pour un patient donné
            List<MedicationDTO> medications = parameterizationClient.getMedicationsByPatientId(patientId);

            // Si la liste de médicaments est vide ou nulle, gérer ce cas
            List<Long> medicationIds = new ArrayList<>();
            if (medications != null && !medications.isEmpty()) {
                for (MedicationDTO medication : medications) {
                    Long medicationKy = Long.valueOf(medication.getMedicationKy());  // Utilisation d'une variable temporaire
                    if (medicationKy != null) {
                        medicationIds.add(medicationKy);
                    }
                }
            }

            // Si aucun médicament n'a été trouvé, initialiser à une liste vide
            if (medicationIds.isEmpty()) {
                medicalRecord.setMedicationIds(Collections.emptyList());
            } else {
                medicalRecord.setMedicationIds(medicationIds);
            }

        } catch (Exception e) {
            // Gérer les exceptions (par exemple, si le microservice Parameterization est hors ligne)
            throw new RuntimeException("Failed to retrieve medications for patient ID: " + patientId, e);
        }

        return medicalRecord;
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(Long medicalRecordId) {
        medicalRecordRepository.deleteById(medicalRecordId);
    }
}
