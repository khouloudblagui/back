package com.example.parameterization.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
// @Table annotation to specify the table details
@Table(name = "PhysicalTreatment")
public class PhysicalTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idtreatment;

    @Column(name = "treatmentName", unique = true)
    private String phyTrName;

    @Column(name ="TreatmentDescription" )
    private String phyTrDesc;

    // la durée prévu pour ce traitement
    @Column(name = "duration")
    private String phyTrDuration;

    @Column(name = "notes")
    private String phyTrNote;

    @Override
    public String toString() {
        return "PhysicalTreatment{" +
                "PhysicalTreatment_Key=" + idtreatment +
                ", treatmentName='" + phyTrName + '\'' +
                ", treatmentDescription='" + phyTrDesc + '\'' +
                ", duration='" + phyTrDuration + '\'' +
                ", notes='" + phyTrNote + '\'' +
                '}';
    }
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = PhysicalTreatmentCategory.class)
    @JsonIgnoreProperties("physicalTreatments")
    @JoinColumn(name = "categoryId")
    private PhysicalTreatmentCategory physicalTreatmentCategory;
}
