package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialization;
    private String email;
    private String phoneNumber;

}
