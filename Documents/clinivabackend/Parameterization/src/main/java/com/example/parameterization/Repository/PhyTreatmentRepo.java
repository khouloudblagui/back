package com.example.parameterization.Repository;

import com.example.parameterization.Entity.PhysicalTreatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhyTreatmentRepo extends JpaRepository<PhysicalTreatment, Long> {
    List<PhysicalTreatment> findByphyTrNameContaining(String criteria);
}
