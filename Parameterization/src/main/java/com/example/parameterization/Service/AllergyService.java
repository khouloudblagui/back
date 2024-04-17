package com.example.parameterization.Service;

import com.example.parameterization.Entity.Allergy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AllergyService {
    List<Allergy> getAllAllergies();

    Allergy addAllergy(Allergy iAllergy);
    Allergy updateAllergy(Allergy iAllergy);
    void removeAllergy(Long iAllergyKy);
    List<Allergy> retrieveAllergiesByCriteria(String iCriteria);
    Optional<Allergy> viewDetails(Long iAllergyKy);
}
