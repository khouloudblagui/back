package org.example.authentification.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.authentification.User.Permission.*;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_CREATE,
                  ADMIN_DELETE,
                  ADMIN_UPDATE,
                  MANAGER_CREATE,
                  MANAGER_DELETE,
                  MANAGER_UPDATE,
                  MANAGER_READ


          )
  ),
  MANAGER(
          Set.of(
                  MANAGER_CREATE,
                  MANAGER_DELETE,
                  MANAGER_UPDATE,
                  MANAGER_READ
          )
  ),
  DOCTOR(
          Set.of(
                  DOCTOR_CREATE,
                  DOCTOR_DELETE,
                  DOCTOR_UPDATE,
                  DOCTOR_READ
          )
  ),
  PATIENT(
          Set.of(
                  PATIENT_CREATE,
                  PATIENT_DELETE,
                  PATIENT_UPDATE,
                  PATIENT_READ
          )
  ),

  NURSE(
          Set.of(
                  NURSE_CREATE,
                  NURSE_DELETE,
                  NURSE_UPDATE,
                  NURSE_READ
          )
  )
  ;
  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities(){
    var authorities=  getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}

