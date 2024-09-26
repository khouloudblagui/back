package org.example.authentification.services;



import org.example.authentification.dto.Response;
import org.example.authentification.dto.UserDto;
import org.example.authentification.dto.doctorDto;
import org.example.authentification.entites.User;


import java.util.List;
import java.util.Optional;

public interface AdministrateurService {
    Response adddoctor(doctorDto agentDto);
    Response revokeAccount(String cin, boolean activate);
    List<UserDto> getAllUsersExceptAdmin();
    Response updateUser(User user);
    Optional<User> findUserById(Long id);
}