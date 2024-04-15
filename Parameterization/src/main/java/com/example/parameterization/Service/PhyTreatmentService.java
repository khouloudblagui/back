package com.example.parameterization.Service;

import com.example.parameterization.Entity.PhysicalTreatment;

import java.util.List;

public interface PhyTreatmentService {
    List<PhysicalTreatment> getAllTreatments();
    PhysicalTreatment getTreatmentById(long iiId);
    PhysicalTreatment saveTreatment(PhysicalTreatment iTreatment);
    PhysicalTreatment updateTreatment(PhysicalTreatment iTreatment, long iId);

    void deleteTreatment(long iiId);
    List<PhysicalTreatment> retrievePhyTreatmentByCriteria(String iiCriteria);
}
