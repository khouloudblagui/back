package org.example.authentification.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.authentification.auth.RegisterRequest;
import org.example.authentification.entites.Patient;
import org.example.authentification.entites.doctor;


@Getter
@Setter
public class PatientDto extends RegisterRequest {
    public static Patient toEntity(PatientDto request) {
        return Patient.builder()
                .isEnabled(true)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .build();
    }
    public static RegisterRequest fromEntity(Patient patient) {
        if (patient == null) {
            return null;
        }
        return PatientDto.builder()
                .firstname(patient.getFirstname())
                .lastname(patient.getLastname())
                .email(patient.getEmail())
                .password(patient.getPassword())
                .phone(patient.getPhone())
                .build();
    }




}
