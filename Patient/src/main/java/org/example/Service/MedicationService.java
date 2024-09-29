package org.example.Service;

import org.example.DTO.MedicationDTO;
import org.example.Feign.ParameterizationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private ParameterizationClient parameterizationClient;

    public List<MedicationDTO> getAllMedications() {
        return parameterizationClient.getAllMedications();
    }

    public MedicationDTO getMedicationById(Integer medicationId) {
        return parameterizationClient.getMedicationById(medicationId);
    }
}