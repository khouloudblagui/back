package org.example.Service;

import org.example.DTO.PatientDTO;
import org.example.Feign.DoctorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private DoctorClient doctorClient;

    public PatientDTO getPatientById(Long id) {
        // Appel au microservice Doctor via Feign pour obtenir le PatientDTO
        return doctorClient.getPatientById(id);
    }
}
