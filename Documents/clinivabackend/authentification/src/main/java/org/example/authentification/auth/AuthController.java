package org.example.authentification.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.authentification.User.Personal;
import org.example.authentification.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

  private final AuthenticationService service;

  private final PasswordResetService passwordResetService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }

  @PutMapping("/update/{user_ky}")
  public ResponseEntity<AuthenticationResponse> updateUser(
          @PathVariable Integer user_ky,
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.updateUser(user_ky, request));
  }

  @PutMapping("/update-personal/{user_ky}")
  public ResponseEntity<AuthenticationResponse> updatePersonal(
          @PathVariable Integer user_ky,
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.updatePersonal(user_ky, request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request
  ) {

    return ResponseEntity.ok(service.authenticate(request));
  }
  @PostMapping("/refresh-token")
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

  @GetMapping("/user-details")
  public ResponseEntity<UserDetails> getUserDetails() {
    UserDetails userDetails = service.getCurrentUserDetails();
    return ResponseEntity.ok(userDetails);
  }
  /////////////Crud personal
  @PostMapping("/register_personal")
  public ResponseEntity<AuthenticationResponse> registerPersonal(
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerPersonal(request));
  }
  @GetMapping("/all-personal")
  public List<Personal> getPersonals(){
    return service.getPersonal();
  }
  //Delete
  @DeleteMapping("/delete/{user_ky}")
  private void deletePer(@PathVariable("user_ky")Integer user_ky)
  {
    service.delete(user_ky);
  }

  //get personal
  @RequestMapping("/view/{user_ky}")
  private Optional<Personal> getPersonal(@PathVariable("user_ky") Integer user_ky) {
    return service.getPersonalById(user_ky);
  }
  //update personal
  @PutMapping(value="/edit/{user_ky}")
  private ResponseEntity<?> update(@RequestBody Personal personal, @PathVariable(name="user_ky")Integer user_ky) {

    personal.setUser_ky(user_ky);
    Personal per = service.saveorUpdate(personal);
    return ResponseEntity.ok(per);

  }

  @GetMapping("/details/{email}")
  public ResponseEntity<User> getUserDetailsByEmail(@PathVariable String email) {
    User user = service.findUserparEmail(email);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  @GetMapping("/all-user")
  public List<User> getUser(){
    return service.getUsers();
  }

  @PostMapping("/request-password-reset")
  public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> emailRequest) {
    String email = emailRequest.get("email");
    passwordResetService.requestPasswordReset(email);
    return ResponseEntity.ok("Password reset email sent");
  }

  @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody Map<String, String> passwordRequest) {
    String newPassword = passwordRequest.get("newPassword");
    passwordResetService.resetPassword(token, newPassword);
    return ResponseEntity.ok("Password has been reset");
  }
}
