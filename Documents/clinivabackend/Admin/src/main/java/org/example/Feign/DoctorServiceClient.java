package org.example.Feign;

import org.example.DTO.AppointmentDTO;
import org.example.DTO.DoctorDTO;
import org.example.DTO.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
public interface DoctorServiceClient {

    @GetMapping("/doctors/{doctorId}")
    DoctorDTO getDoctorById(@PathVariable Long doctorId);

    @GetMapping("/doctors/patients/{patientId}")
    PatientDTO getPatientById(@PathVariable Long patientId);

    @GetMapping("/doctors/appointments/{appointmentId}")
    AppointmentDTO getAppointmentById(@PathVariable Long appointmentId);
}

