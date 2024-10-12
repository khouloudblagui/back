package org.example.authentification.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Getter
@Setter
@Entity
@SuperBuilder
@DiscriminatorValue("doctor")
@Table
@NoArgsConstructor
public class doctor extends User {

   /* @Column(name = "specialty")
    private String specialization;

    @Column(name = "department")
    private String department;

    @Column(name = "degree")
    private String degree;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_joining")
    private Date dateOfJoining;*/


}
