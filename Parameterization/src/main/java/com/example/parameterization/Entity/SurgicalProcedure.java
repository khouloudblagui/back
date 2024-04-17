package com.example.parameterization.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "SurgicalProcedure")

public class SurgicalProcedure implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPT_Ky",nullable = false)
    private Long cptky;
    @Column(name = "CPT_code")
    private String cptCode;
    @Column(name = "CPT_Desc")
    private String cptDesc;
    @Column(name="CPT_Category")
    private String cptCategory;

}
