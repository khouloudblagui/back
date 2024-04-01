package com.example.parameterization.Repository;
import com.example.parameterization.Entity.MedicIngredientLink;
import com.example.parameterization.Entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MedicIngredientLinkRepo extends JpaRepository<MedicIngredientLink, Integer> {

    void deleteByMed(@Param("medicament") Medication medicament);

}
