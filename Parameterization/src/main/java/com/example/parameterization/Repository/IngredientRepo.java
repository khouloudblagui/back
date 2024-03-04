package com.example.parameterization.Repository;

import com.example.parameterization.Entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepo extends JpaRepository<Ingredient, Integer>{
}
