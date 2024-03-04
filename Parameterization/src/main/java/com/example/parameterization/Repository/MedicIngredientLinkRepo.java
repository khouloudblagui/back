package com.example.parameterization.Repository;
import com.google.common.primitives.UnsignedInteger;
import com.example.parameterization.Entity.MedicIngredientLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicIngredientLinkRepo extends JpaRepository<MedicIngredientLink, Integer> {
}
