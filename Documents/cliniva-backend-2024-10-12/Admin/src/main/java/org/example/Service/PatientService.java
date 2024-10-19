package org.example.Service;

import org.example.DTO.PatientDTO;
import org.example.Feign.DoctorServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private DoctorServiceClient doctorServiceClient;

    public PatientDTO getPatientDetails(Long patientId) {
        return doctorServiceClient.getPatientById(patientId);
    }
}
