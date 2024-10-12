package org.example.authentification.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentification.dto.PatientDto;
import org.example.authentification.dto.Response;
import org.example.authentification.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/registerPatient")
  public Response register(
          @RequestBody @Valid PatientDto userRequest,
          HttpServletRequest request
  )  {
    return service.register(userRequest,request);
  }



  @PostMapping("/authenticate")
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

  // Nouveau point de terminaison pour valider un token
  @GetMapping("/validate-token/{token}")
  public ResponseEntity<Boolean> validateToken(@PathVariable String token) {
    boolean isValid = service.validateToken(token);  // Appel de la méthode de validation dans AuthenticationService
    return new ResponseEntity<>(isValid, HttpStatus.OK);
  }

  @GetMapping("/user-by-token/{token}")
  public ResponseEntity<UserDto> getUserByToken(@PathVariable String token) {
    UserDto userDto = service.getUserByToken(token);
    return ResponseEntity.ok(userDto);
  }


}
