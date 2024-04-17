package com.example.parameterization.Service;

import com.example.parameterization.Entity.AdverseEffect;

import java.util.List;
import java.util.Optional;

public interface AdverseEffectService {
    void create(AdverseEffect iAdverseEffect);

    List<AdverseEffect> retrieveAdverseEffect();

    Optional<AdverseEffect> getAdverseEffectById(Long iIdAdverseEffect);

    void update(AdverseEffect iAdverseEffect);

    void delete(Long iIdAdverseEffect);
}
