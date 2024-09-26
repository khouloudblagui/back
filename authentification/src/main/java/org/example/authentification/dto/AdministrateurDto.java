package org.example.authentification.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.authentification.auth.RegisterRequest;
import org.example.authentification.entites.Administrateur;


@Getter
@Setter
public class AdministrateurDto extends RegisterRequest {

    public static Administrateur toEntity(AdministrateurDto request) {
        return Administrateur.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .build();
    }


}
