package com.example.parameterization.Repository;

import com.example.parameterization.Entity.Symptoms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SymptomRepo extends JpaRepository<Symptoms, Long> {
    List<Symptoms> findAllBySymptomNameContaining(String name);
}
