package org.example.authentification.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.authentification.auth.RegisterRequest;
import org.example.authentification.entites.doctor;


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
