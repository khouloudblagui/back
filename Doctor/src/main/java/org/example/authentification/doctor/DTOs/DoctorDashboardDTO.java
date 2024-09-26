package org.example.authentification.doctor.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.example.authentification.doctor.Entity.Doctor;

@Getter
@Setter
public class DoctorDashboardDTO {
    private String doctorName;
    private String specialization;
    private int patientCount;
    private int appointmentCount;

    public DoctorDashboardDTO(Doctor doctor) {
        this.doctorName = doctor.getFirstName() + " " + doctor.getLastName();
        this.specialization = doctor.getSpecialization();
        this.patientCount = doctor.getPatients().size();
        this.appointmentCount = doctor.getAppointments().size();
    }

    // Getters and Setters
}
