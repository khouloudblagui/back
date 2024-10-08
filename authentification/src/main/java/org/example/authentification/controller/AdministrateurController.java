package org.example.authentification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentification.dto.Response;
import org.example.authentification.dto.UserDto;
import org.example.authentification.dto.doctorDto;
import org.example.authentification.entites.User;
import org.example.authentification.services.AdministrateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdministrateurController {
    private final AdministrateurService administrateurService;



    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/adddoctor")
    public Response adddoctor(
            @RequestBody @Valid doctorDto userRequest

    )  {
        return administrateurService.adddoctor(userRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateuser/{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable Long userId, @RequestBody @Valid UserDto userDto) {
        Optional<User> userOptional = administrateurService.findUserById(userId);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            // Mettre à jour les champs modifiables de l'utilisateur
            userToUpdate.setFirstname(userDto.getFirstname());
            userToUpdate.setLastname(userDto.getLastname());
            userToUpdate.setEmail(userDto.getEmail());
            userToUpdate.setPhone(userDto.getPhone());
            userToUpdate.setPassword(userDto.getPassword());

            // Appeler le service pour effectuer la mise à jour
            Response response = administrateurService.updateUser(userToUpdate);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/revokeaccount")
    public ResponseEntity<Response> revokeAccount(@RequestParam String email, @RequestParam boolean activate) {
        Response response = administrateurService.revokeAccount(email, activate);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/usersexceptadmin")
    public ResponseEntity<List<UserDto>> getAllUsersExceptAdmin() {
        List<UserDto> users = administrateurService.getAllUsersExceptAdmin();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{Id}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> userOptional = administrateurService.findUserById(userId);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}

