package org.example.Service;

import org.example.DTO.AppointmentDTO;
import org.example.Feign.DoctorServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private DoctorServiceClient doctorServiceClient;

    public AppointmentDTO getAppointmentDetails(Long appointmentId) {
        return doctorServiceClient.getAppointmentById(appointmentId);
    }
}
