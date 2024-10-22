package org.example.authentification.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.authentification.User.Personal;
import org.example.authentification.User.PersonalRepo;
import org.example.authentification.User.UserRepo;
import org.example.authentification.config.JwtService;
import org.example.authentification.User.User;
import org.example.authentification.token.Token;
import org.example.authentification.token.TokenRepo;
import org.example.authentification.token.TokenType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class AuthenticationService {


  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  private final TokenRepo tokenRepo;

  private final AuthenticationManager authenticationManager;

  private final PersonalRepo personalRepo;
  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();
    var savedUser = userRepo.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .role(user.getRole().toString())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .build();
  }

  public AuthenticationResponse updateUser(Integer user_ky, RegisterRequest request) {
    var user = userRepo.findById(user_ky)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + user_ky));

    // Update user fields with the new values
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    user.setEmail(request.getEmail());

    // Only update the password if it is provided
    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    var savedUser = userRepo.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponse updatePersonal(Integer user_ky, RegisterRequest request) {
    var personal = personalRepo.findById(user_ky)
            .orElseThrow(() -> new RuntimeException("Personal not found with id: " + user_ky));

    // Update user fields with the new values
    personal.setFirstname(request.getFirstname());
    personal.setLastname(request.getLastname());
    personal.setEmail(request.getEmail());



    // Only update the password if it is provided
    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
      personal.setPassword(passwordEncoder.encode(request.getPassword()));
    }
    personal.setRole(request.getRole());
    personal.setDepartment(request.getDepartment());
    personal.setUploadedFile(request.getUploadedFile());

    var savedPersonal = personalRepo.save(personal);
    var jwtToken = jwtService.generateToken(personal);
    var refreshToken = jwtService.generateRefreshToken(personal);
    saveUserToken(savedPersonal, jwtToken);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponse registerPersonal(RegisterRequest request) {
    // Crée un Personal à partir de la RegisterRequest
    Personal personal = Personal.createPersonal(request, passwordEncoder);

    // Enregistre le Personal dans le référentiel
    var savedPersonal = userRepo.save(personal);

    // Génère le jeton JWT et le rafraîchissement du Personal enregistré
    var jwtToken = jwtService.generateToken(savedPersonal);
    var refreshToken = jwtService.generateRefreshToken(savedPersonal);

    // Enregistre le jeton utilisateur
    saveUserToken(savedPersonal, jwtToken);

    // Retourne la réponse d'authentification avec le jeton JWT et le rafraîchissement
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }


  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user = userRepo.findByEmail(request.getEmail())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    //revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .role(user.getRole().toString())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepo.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getUser_ky());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepo.saveAll(validUserTokens);
  }
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userRepo.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }


  public UserDetails getCurrentUserDetails() {
    // Récupérer les détails de l'utilisateur actuellement authentifié à partir du contexte de sécurité
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      return (UserDetails) principal; // Si l'utilisateur est authentifié, retournez ses détails
    } else {
      return null; // Si l'utilisateur n'est pas authentifié ou si les détails ne sont pas disponibles, retournez null
    }
  }

  public List<Personal> getPersonal() {
    return personalRepo.findAll();
  }

  public void delete(Integer user_ky) {

    personalRepo.deleteById(user_ky);
  }
  public Optional<Personal> getPersonalById(Integer user_ky) {
    return personalRepo.findById(user_ky);
  }
  public Personal saveorUpdate(Personal personal) {

    personalRepo.save(personal);
    return personal;
  }


  public User findUserparEmail(String email) {
    Optional<User> userOptional = userRepo.findByEmail(email);
    return userOptional.orElse(null); // Retourner l'utilisateur s'il est présent, sinon null
  }

  public List<User> getUsers() {
    return userRepo.findAll();
  }

}
