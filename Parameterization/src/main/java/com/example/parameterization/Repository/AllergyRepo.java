package com.example.parameterization.Repository;

import com.example.parameterization.Entity.Allergy;
import com.example.parameterization.Enum.AllergyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



public interface AllergyRepo extends JpaRepository<Allergy,Long> {

    List<Allergy> findAllByAllergyNameContaining(String allergyName);

    List<Allergy> findAllByAllergyType(AllergyType iAllergyType);

    Optional<Allergy> findByAllergyName(String iAllergyName);

}
