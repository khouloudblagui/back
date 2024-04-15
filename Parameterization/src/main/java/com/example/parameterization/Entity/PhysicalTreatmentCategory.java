package com.example.parameterization.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PhysicalTreatmentCategory")
public class PhysicalTreatmentCategory {
    @Id
    // Annotation for auto_increment the key (id)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryid;

    @Column(name ="categoryName",unique = true)
    private String phyCategoryName;
    @Column(name ="categoryDescription")
    private String phyCategoryDesc;

    @Override
    public String toString() {
        return "PhysicalTreatmentCategory{" +
                ", categoryName='" + phyCategoryName + '\'' +
                ", categoryDescription='" + phyCategoryDesc + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "physicalTreatmentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("physicalTreatmentCategory")
    private Set<PhysicalTreatment> physicalTreatments;

}
