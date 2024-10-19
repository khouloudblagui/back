package org.example.authentification.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.example.authentification.auth.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Entity
@DiscriminatorValue("Personal")
public class Personal extends User {
    private String department;
    private String uploadedFile;

    // Constructeur statique pour créer une instance de Personal
    public static Personal createPersonal(RegisterRequest request, PasswordEncoder passwordEncoder) {
        Personal personal = new Personal();
        personal.setFirstname(request.getFirstname());
        personal.setLastname(request.getLastname());
        personal.setEmail(request.getEmail());
        personal.setPassword(passwordEncoder.encode(request.getPassword()));
        personal.setRole(request.getRole());
        personal.setDepartment(request.getDepartment());
        personal.setUploadedFile(request.getUploadedFile());
        return personal;
    }
}

