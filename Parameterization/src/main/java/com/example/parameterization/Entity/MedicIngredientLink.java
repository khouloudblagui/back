package com.example.parameterization.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medic_ingredient_link")
public class MedicIngredientLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medic_ingredient_link_ky" , unique = true)
    private Integer medicIngredientLinkKy;

    @ManyToOne
    @JoinColumn(name = "medication_ky", nullable = false)
    private Medication med;

    @ManyToOne
    @JoinColumn(name = "ingredient_ky", nullable = false)
    private Ingredient ing;

}
