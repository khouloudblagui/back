
package org.example.authentification.dto;

import lombok.Builder;
import lombok.Data;
import org.example.authentification.config.SimpleGrantedAuthorityImpl;
import org.example.authentification.entites.Role;
import org.example.authentification.entites.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String password;
    private Role role;
    private boolean isEnabled;
    private List<SimpleGrantedAuthorityImpl> authorities;

    public static UserDto fromEntity(User request) {
        return UserDto.builder()
                .id(request.getId())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .isEnabled(request.getIsEnabled())
                // Ajout de la conversion des autorités
                .authorities(request.getAuthorities().stream()
                        .map(auth -> new SimpleGrantedAuthorityImpl(auth.getAuthority()))
                        .collect(Collectors.toList()))
                .build();
    }









   /* public static UserDto fromEntity(User request) {

        return UserDto.builder()
                .id(request.getId())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .phone(request.getPhone())
                .isEnabled(request.getIsEnabled())
                .build();
    }*/















    /* public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role)); // Préfixe ROLE_
    }*/




    // Convertir le rôle en autorité pour Spring Security
   /* public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si le rôle est défini, le convertir en SimpleGrantedAuthority
        if (role != null) {
            return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())); // Préfixe ROLE_ pour les autorités
        }
        // Si aucun rôle n'est défini, retourner une liste vide
        return List.of();
    }*/



   /* public static User toEntity(UserDtoo dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setPhone(dto.getPhone());

        return user;
    }*/
}
