package com.example.parameterization.Repository;

import com.example.parameterization.Entity.PhysicalTreatmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhyTrCategoryRepo extends JpaRepository<PhysicalTreatmentCategory, Long> {
    List<PhysicalTreatmentCategory> findByphyCategoryNameContaining(String criteria);
}
