package org.example.Service;

import org.example.DTO.DoctorDTO;
import org.example.Feign.DoctorServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorServiceClient doctorServiceClient;

    public DoctorDTO getDoctorDetails(Long doctorId) {
        return doctorServiceClient.getDoctorById(doctorId);
    }
}
