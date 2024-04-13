package com.example.parameterization.Repository;

import com.example.parameterization.Entity.AdverseEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdverseEffectRepo extends JpaRepository<AdverseEffect, Long> {
}
