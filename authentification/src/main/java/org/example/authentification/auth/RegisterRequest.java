package org.example.authentification.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.authentification.entites.Role;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String phone;
  private String email;
  private String password;
  private Role role;
  private Boolean isEnabled;

}
