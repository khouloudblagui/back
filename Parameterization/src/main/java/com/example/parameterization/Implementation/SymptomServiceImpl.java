package com.example.parameterization.Implementation;

import com.example.parameterization.Entity.Symptoms;
import com.example.parameterization.Repository.SymptomRepo;
import com.example.parameterization.Service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SymptomServiceImpl implements SymptomService {
    @Autowired
    private SymptomRepo symptomRepository;

    @Override
    public List<Symptoms> getAllSymptoms() {
        return symptomRepository.findAll();
    }
    @Override
    public Symptoms addSymptom(Symptoms iSymptom) {
        return symptomRepository.save(iSymptom);
    }

    @Override
    public void removeSymptom(Long iId) {
        symptomRepository.deleteById(iId);
    }
    @Override
    public Symptoms getSymptomById(Long iId) {
        return symptomRepository.findById(iId).orElse(null);
    }
    @Override
    public Symptoms updateSymptom(Long iId, Symptoms updatedSymptom) {
        Optional<Symptoms> symptomOptional = symptomRepository.findById(iId);
        if (symptomOptional.isPresent()) {
            Symptoms existingSymptom = symptomOptional.get();
            existingSymptom.setSymptomName(updatedSymptom.getSymptomName());
            existingSymptom.setSymptomDesc(updatedSymptom.getSymptomDesc());
            return symptomRepository.save(existingSymptom);
        } else {
            return null;
        }
    }
    public List<Symptoms> retrieveSymptomsByCriteria(String criteria) {
        // Implémentez la logique de recherche en fonction du critère spécifié
        return symptomRepository.findAllBySymptomNameContaining(criteria);
    }
}
