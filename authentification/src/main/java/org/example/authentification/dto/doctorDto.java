package org.example.authentification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.authentification.auth.RegisterRequest;
import org.example.authentification.entites.Role;
import org.example.authentification.entites.doctor;

import java.util.Date;


@Getter
@Setter
public class doctorDto extends RegisterRequest {

    public static doctor toEntity(doctorDto request) {
        return doctor.builder()
                .isEnabled(true)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .build();
    }
    public static doctorDto fromEntity(doctor request) {
        if (request == null) {
            return null;
        }

        return (doctorDto) doctorDto.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .build();
    }

}















/*
public class doctorDto {

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private Boolean isEnabled;

    private String specialization;
    private String department;
    private String degree;
    private Date dateOfJoining;

    // Convertir un doctorDto en entité Doctor
    public static doctor toEntity(doctorDto request) {
        return doctor.builder()
                .isEnabled(true)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .specialization(request.getSpecialization())
                .department(request.getDepartment())
                .degree(request.getDegree())
                .dateOfJoining(request.getDateOfJoining())
                .build();
    }

    // Convertir une entité Doctor en doctorDto
    public static doctorDto fromEntity(doctor request) {
        if (request == null) {
            return null;
        }

        return doctorDto.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .specialization(request.getSpecialization())
                .department(request.getDepartment())
                .degree(request.getDegree())
                .dateOfJoining(request.getDateOfJoining())
                .build();
    }
}

*/

