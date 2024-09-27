package org.example.doctor.Exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id) {
        super("Patient not found with ID: " + id);
    }
}
