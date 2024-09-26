package org.example.authentification.services;


import org.example.authentification.dto.Response;
import org.example.authentification.entites.User;
import org.example.authentification.entites.VerificationToken;
import org.springframework.http.ResponseEntity;

public interface VerificationTokenService {
    void saveUserVerificationToken(User user, String token);
    String validateToken(String token);
    ResponseEntity<Response> verifyEmail(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
}