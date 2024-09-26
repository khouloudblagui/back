package org.example.authentification.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.authentification.dto.EmailDetails;
import org.example.authentification.dto.Response;
import org.example.authentification.dto.UserDto;
import org.example.authentification.dto.doctorDto;
import org.example.authentification.entites.Role;
import org.example.authentification.entites.User;
import org.example.authentification.entites.doctor;
import org.example.authentification.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdministrateurServiceImpl implements AdministrateurService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final EmailService emailService;

    @Override
    public Response adddoctor(doctorDto doctorDto) {

        boolean userExists = repository.findAll()
                .stream()
                .anyMatch(user -> doctorDto.getEmail().equalsIgnoreCase(user.getEmail()));

        if (userExists) {
            return (Response.builder()
                    .responseMessage("User with provided email  already exists!")
                    .build());
        }

        User user;
        User savedUser = null;

        user = new doctor();
        user = doctorDto.toEntity((doctorDto) doctorDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.DOCTOR);
        var saveUsers = repository.save(user);

        // Envoyer un email à doctor
        senddoctorRegistrationEmail(doctorDto, null); // Vous pouvez passer HttpServletRequest si nécessaire

        return Response.builder()
                .responseMessage("register")
                .email(user.getEmail())
                .build();
    }

    private void senddoctorRegistrationEmail(doctorDto doctorDto, HttpServletRequest request) {
        // Construire le contenu de l'email pour l'doctor
        String subject = "Welcome to our platform";
        String content = "Dear " + doctorDto.getFirstname() + ",\n\n"
                + "Thank you for registering as an doctor.\n"
                + "Your account has been created successfully.\n"
                + "Below is your login information:\n"
                + "Email: " + doctorDto.getEmail() + "\n"
                + "<b>Password: " + doctorDto.getPassword() + "</b>\n\n"
                + "Please note that this is your temporary password. "
                + "We strongly recommend that you change your password immediately after logging in for the first time.\n\n"
                + "Please keep this email for your records.\n\n"
                + "Best regards,\nYour Platform Team";

        // Envoyer l'email à l'doctor
        EmailDetails emailDetails = EmailDetails.builder()
                .to(doctorDto.getEmail())
                .subject(subject)
                .messageBody(content)
                .build();

        emailService.sendMail(emailDetails);
    }

    @Override
    public Response revokeAccount(String email, boolean activate) {
        // Recherchez l'utilisateur par email dans votre système
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return Response.builder()
                    .responseMessage("Utilisateur non trouvé pour l'email spécifié")
                    .build();
        }

        // Extraire l'utilisateur de l'Optional
        User user = userOptional.get();

        // Révoquez ou activez le compte de l'utilisateur en fonction du boolean "activate"
        user.setIsEnabled(activate);
        repository.save(user);

        if (activate) {
            return Response.builder()
                    .responseMessage("Le compte de l'utilisateur a été activé avec succès")
                    .email(user.getEmail()) // Inclure l'adresse e-mail de l'utilisateur dans la réponse
                    .build();
        } else {
            return Response.builder()
                    .responseMessage("Le compte de l'utilisateur a été révoqué avec succès")
                    .email(user.getEmail()) // Inclure l'adresse e-mail de l'utilisateur dans la réponse
                    .build();
        }
    }

    @Override
    public List<UserDto> getAllUsersExceptAdmin() {
        List<User> allUsers = repository.findAll();
        // Exclure les utilisateurs ayant le rôle ADMINISTRATEUR
        return allUsers.stream()
                .filter(user -> user.getRole() != Role.ADMIN)
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return repository.findById(id);
    }
    @Override
    public Response updateUser(User user) {
        try {
            repository.save(user);
            return Response.builder()
                    .responseMessage("L'utilisateur a été mis à jour avec succès")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .responseMessage("Une erreur s'est produite lors de la mise à jour de l'utilisateur")
                    .build();
        }
    }


}