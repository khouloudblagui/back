package com.example.parameterization.Implementation;

import com.example.parameterization.Entity.AdverseEffect;
import com.example.parameterization.Repository.AdverseEffectRepo;
import com.example.parameterization.Service.AdverseEffectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class AdverseEffectServiceImpl implements AdverseEffectService {

    private final AdverseEffectRepo adverseEffectRepository;

    public AdverseEffectServiceImpl(AdverseEffectRepo adverseEffectRepository) {
        this.adverseEffectRepository = adverseEffectRepository;
    }

    @Override
    public void create(AdverseEffect iAdverseEffect) {
        this.adverseEffectRepository.save(iAdverseEffect);
    }

    @Override
    public List<AdverseEffect> retrieveAdverseEffect() {
        List<AdverseEffect> adverseEffects = this.adverseEffectRepository.findAll();
        for (AdverseEffect adverseEffect : adverseEffects) {
            adverseEffect.getVaccinations().size(); // Fetch vaccinations
        }
        return adverseEffects;
    }

    @Override
    public Optional<AdverseEffect> getAdverseEffectById(Long iIdAdverseEffect) {
        return this.adverseEffectRepository.findById(iIdAdverseEffect);
    }

    @Override
    public void update(AdverseEffect iAdverseEffect) {
        this.adverseEffectRepository.save(iAdverseEffect);
    }

    @Override
    public void delete(Long iIdAdverseEffect) {
        this.adverseEffectRepository.deleteById(iIdAdverseEffect);
    }
}
