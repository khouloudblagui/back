package org.example.Service;

import org.example.DTO.AppointmentDTO;
import org.example.Feign.DoctorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private DoctorClient doctorClient;

    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        return doctorClient.getAppointmentsByPatientId(patientId);
    }

    public AppointmentDTO getAppointmentById(Long appointmentId) {
        return doctorClient.getAppointmentById(appointmentId);
    }
}
