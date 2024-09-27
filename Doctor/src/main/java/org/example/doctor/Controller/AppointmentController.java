package org.example.doctor.Controller;

import org.example.Enum.AppointmentStatus;
import org.example.doctor.Entity.Appointment;
import org.example.doctor.Service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Créer un nouveau rendez-vous
    @PostMapping("/")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    // Récupérer un rendez-vous par son ID
    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    // Mettre à jour un rendez-vous existant
    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long appointmentId,
                                                         @RequestBody Appointment updatedAppointment) {
        Appointment appointment = appointmentService.updateAppointment(appointmentId, updatedAppointment);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    // Supprimer un rendez-vous
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Récupérer tous les rendez-vous d'un docteur
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Récupérer tous les rendez-vous d'un patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Récupérer tous les rendez-vous par statut
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable AppointmentStatus status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
