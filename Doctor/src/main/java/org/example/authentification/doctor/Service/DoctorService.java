package org.example.authentification.doctor.Service;

import org.example.authentification.doctor.DTOs.DoctorDashboardDTO;
import org.example.authentification.doctor.Entity.Doctor;
import org.example.authentification.doctor.Entity.Patient;
import org.example.authentification.doctor.Entity.Appointment;

import java.util.List;

public interface DoctorService {
    DoctorDashboardDTO getDoctorDashboard(Long doctorId);
    List<Appointment> getAppointments(Long doctorId);
    List<Patient> getPatients(Long doctorId);
    Doctor updateSettings(Doctor doctor);


}
