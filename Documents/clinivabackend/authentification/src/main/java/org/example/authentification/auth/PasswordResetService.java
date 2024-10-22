package org.example.authentification.auth;

import org.example.authentification.User.UserRepo;
import org.example.authentification.config.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    public void requestPasswordReset(String email) {
        logger.info("Requesting password reset for email: {}", email);

        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Générer un token de réinitialisation de mot de passe
        String token = jwtService.generatePasswordResetToken(user);

        // Envoyer un email à l'utilisateur avec le lien de réinitialisation
        String resetLink = "http://localhost:4200/#/authentication/locked?token=" + token;
        emailService.sendEmail(email, "Password Reset Request", "Click the link to reset your password: " + resetLink);

        logger.info("Password reset link sent to email: {}", email);
    }

    public void resetPassword(String token, String newPassword) {
        logger.info("Resetting password with token: {}", token);

        String email = jwtService.extractUsername(token);
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (jwtService.isTokenValid(token, user)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
            logger.info("Password has been reset for user with email: {}", email);
        } else {
            throw new RuntimeException("Invalid or expired token");
        }
    }
}


