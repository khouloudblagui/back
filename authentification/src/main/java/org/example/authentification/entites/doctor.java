package org.example.authentification.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@Entity
@Table
@SuperBuilder
@DiscriminatorValue("doctor")
@NoArgsConstructor
public class doctor extends User {


}
