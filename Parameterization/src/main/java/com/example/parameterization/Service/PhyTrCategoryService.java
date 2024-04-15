package com.example.parameterization.Service;

import com.example.parameterization.Entity.PhysicalTreatmentCategory;

import java.util.List;

public interface PhyTrCategoryService {
    List<PhysicalTreatmentCategory> getAllPhyTrCategories();
    PhysicalTreatmentCategory getPhyTrCategoryById(long iId);
    PhysicalTreatmentCategory addPhyTrCategory(PhysicalTreatmentCategory iPhyTrCategory);
    PhysicalTreatmentCategory updatePhyTrCategory(PhysicalTreatmentCategory iPhyTrCategory, long iId);
    void deletePhyTrCategory(long iId);
    List<PhysicalTreatmentCategory> retrievePhyTrCategoryByCriteria(String iCriteria);
}
