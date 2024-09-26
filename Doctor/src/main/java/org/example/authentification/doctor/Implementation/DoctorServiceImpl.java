package org.example.authentification.doctor.Implementation;

import org.example.authentification.doctor.DTOs.DoctorDashboardDTO;
import org.example.authentification.doctor.Entity.Appointment;
import org.example.authentification.doctor.Entity.Doctor;
import org.example.authentification.doctor.Entity.Patient;
import org.example.authentification.doctor.Repository.AppointmentRepository;
import org.example.authentification.doctor.Repository.DoctorRepository;
import org.example.authentification.doctor.Service.DoctorService;
import org.example.authentification.doctor.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             AppointmentRepository appointmentRepository,
                             PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public DoctorDashboardDTO getDoctorDashboard(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        // Populate Dashboard DTO
        return new DoctorDashboardDTO(doctor);
    }

    @Override
    public List<Appointment> getAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<Patient> getPatients(Long doctorId) {
        return patientRepository.findByDoctorId(doctorId);
    }

    @Override
    public Doctor updateSettings(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
