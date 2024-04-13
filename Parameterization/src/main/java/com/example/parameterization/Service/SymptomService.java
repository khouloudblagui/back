package com.example.parameterization.Service;

import com.example.parameterization.Entity.Symptoms;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SymptomService {
    List<Symptoms> getAllSymptoms();
    Symptoms addSymptom(Symptoms iSymptom);
    void removeSymptom(Long iId);
    Symptoms getSymptomById(Long iSymptomId);
    Symptoms updateSymptom(Long iId, Symptoms updatedSymptom);
    List<Symptoms> retrieveSymptomsByCriteria(String criteria);
}
