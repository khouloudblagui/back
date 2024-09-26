package org.example.authentification.doctor.Controller;

import org.example.authentification.doctor.DTOs.DoctorDashboardDTO;
import org.example.authentification.doctor.Entity.Doctor;
import org.example.authentification.doctor.Entity.Patient;
import org.example.authentification.doctor.Entity.Appointment;
import org.example.authentification.doctor.Service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/dashboard/{id}")
    public DoctorDashboardDTO getDoctorDashboard(@PathVariable Long id) {
        return doctorService.getDoctorDashboard(id);
    }

    @GetMapping("/appointments/{id}")
    public List<Appointment> getDoctorAppointments(@PathVariable Long id) {
        return doctorService.getAppointments(id);
    }

    @GetMapping("/patients/{id}")
    public List<Patient> getDoctorPatients(@PathVariable Long id) {
        return doctorService.getPatients(id);
    }



    @PutMapping("/settings")
    public Doctor updateDoctorSettings(@RequestBody Doctor doctor) {
        return doctorService.updateSettings(doctor);
    }
}
