package org.example.doctor.Service;

import org.example.Enum.AppointmentStatus;
import org.example.doctor.Entity.Appointment;
import org.example.doctor.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("doctorAppointmentService")
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Créer un nouveau rendez-vous
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Trouver un rendez-vous par son ID
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + appointmentId));
    }

    // Mettre à jour un rendez-vous existant
    public Appointment updateAppointment(Long appointmentId, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointmentById(appointmentId);
        existingAppointment.setDate(updatedAppointment.getDate());
        existingAppointment.setTime(updatedAppointment.getTime());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setStatus(updatedAppointment.getStatus());
        return appointmentRepository.save(existingAppointment);
    }

    // Supprimer un rendez-vous par son ID
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    // Récupérer tous les rendez-vous
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }


    // Récupérer tous les rendez-vous d'un docteur
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Récupérer tous les rendez-vous d'un patient
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Récupérer tous les rendez-vous avec un statut donné
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }
}
