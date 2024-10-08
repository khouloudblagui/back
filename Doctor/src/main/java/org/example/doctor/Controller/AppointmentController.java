package org.example.doctor.Controller;

import org.example.Enum.AppointmentStatus;
import org.example.doctor.DTOs.AppointmentDTO;
import org.example.doctor.Entity.Appointment;
import org.example.doctor.Entity.Doctor;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Service.AppointmentService;
import org.example.doctor.Service.DoctorService;
import org.example.doctor.Service.PatientService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/doctor/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);


    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService  = patientService;
    }

    // Créer un nouveau rendez-vous
    @PostMapping("/")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        // Log pour vérifier les informations reçues
        logger.info("Received request to create appointment: {}", appointmentDTO);

        // Rechercher le docteur par son ID
        Doctor doctor = doctorService.getDoctorById(appointmentDTO.getDoctorId());
        if (doctor == null) {
            logger.error("Doctor not found with ID: {}", appointmentDTO.getDoctorId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Rechercher le patient par son ID
        Patient patient = patientService.getPatientById(appointmentDTO.getPatientId());
        if (patient == null) {
            logger.error("Patient not found with ID: {}", appointmentDTO.getPatientId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Créer une nouvelle entité Appointment à partir des informations du DTO
        Appointment appointment = new Appointment();
        appointment.setName(appointmentDTO.getName());
        appointment.setEmail(appointmentDTO.getEmail());
        appointment.setGender(appointmentDTO.getGender());
        appointment.setDate(appointmentDTO.getDate());
        appointment.setTime(appointmentDTO.getTime());
        appointment.setMobile(appointmentDTO.getMobile());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setInjury(appointmentDTO.getInjury());

        // Log avant de créer le rendez-vous
        logger.info("Creating appointment for patient: {} with doctor: {}", appointmentDTO.getPatientId(), appointmentDTO.getDoctorId());

        // Enregistrer le rendez-vous
        Appointment newAppointment = appointmentService.createAppointment(appointment);

        // Log après la création du rendez-vous
        logger.info("Appointment created successfully with ID: {}", newAppointment.getId());

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

    // Récupérer tous les rendez-vous
    @GetMapping("/")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
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
