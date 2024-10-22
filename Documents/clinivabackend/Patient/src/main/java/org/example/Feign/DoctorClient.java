package org.example.Feign;

import org.example.DTO.AppointmentDTO;
import org.example.DTO.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);

    @GetMapping("/appointments/patient/{patientId}")
    List<AppointmentDTO> getAppointmentsByPatientId(@PathVariable("patientId") Long patientId);

    @GetMapping("/appointments/{appointmentId}")
    AppointmentDTO getAppointmentById(@PathVariable("appointmentId") Long appointmentId);
}
