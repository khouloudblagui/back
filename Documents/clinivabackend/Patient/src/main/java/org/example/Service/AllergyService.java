package org.example.Service;

import org.example.DTO.AllergyDTO;
import org.example.Feign.ParameterizationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergyService {

    @Autowired
    private ParameterizationClient parameterizationClient;

    public List<AllergyDTO> getAllergiesByPatientId(Long patientId) {
        return parameterizationClient.getAllergiesByPatientId(patientId);
    }

    public AllergyDTO getAllergyById(Long allergyId) {
        return parameterizationClient.getAllergyById(allergyId);
    }
}
