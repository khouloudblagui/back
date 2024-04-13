package com.example.parameterization.Implementation;



import com.example.parameterization.Entity.Allergy;
import com.example.parameterization.Repository.AllergyRepo;
import com.example.parameterization.Service.AllergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class AllergyServiceImpl implements AllergyService {
    @Autowired
    private AllergyRepo allergyRepository;
    @Override
    public List<Allergy> getAllAllergies() {
        return allergyRepository.findAll();
    }

    @Override
    public Allergy addAllergy(Allergy iAllergy) {
        return allergyRepository.save(iAllergy);
    }

    @Override
    public Allergy updateAllergy(Allergy iAllergy) {
        return this.allergyRepository.save(iAllergy);
    }


    @Override
    public void removeAllergy(Long iId) {
        allergyRepository.deleteById(iId);
    }

    @Override
    public List<Allergy> retrieveAllergiesByCriteria(String iCriteria) {
        // Implémentez la logique de recherche en fonction du critère spécifié
        return allergyRepository.findAllByAllergyNameContaining(iCriteria);
    }


    @Override
    public Optional<Allergy> viewDetails(Long iId) {

        return allergyRepository.findById(iId);
    }
}
