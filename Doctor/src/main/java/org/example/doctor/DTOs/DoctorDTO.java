package org.example.doctor.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.authentification.dto.UserDto;
import org.example.doctor.Entity.Doctor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String specialization;

    private String department;

    private String degree;

    private Date dateOfJoining;

    // Constructeur qui combine les informations de UserDTO et Doctor
    public DoctorDTO(UserDto user, Doctor doctor) {
        this.id = doctor.getId();
        this.name = user.getFirstname() + " " + user.getLastname();
        this.email = user.getEmail();
        this.mobile = user.getPhone();
        this.specialization = doctor.getSpecialization();
        this.department = doctor.getDepartment();
        this.degree = doctor.getDegree();
        this.dateOfJoining = doctor.getDateOfJoining();
    }
}
