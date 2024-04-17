package com.example.parameterization.Repository;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Enum.MedicationType;
import com.example.parameterization.dto.MedicationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepo extends JpaRepository<Medication, Integer> {
    boolean existsByMedicationNameOrMedicationCode(String medicationName, String medicationCode);
    boolean existsByMedicationNameOrMedicationCodeAndMedicationKyNot(String imedicationName, String imedicationCode, Integer imedicationId);


    List<Medication> findByMedicationNameContainingIgnoreCase(String medicationName);


}
