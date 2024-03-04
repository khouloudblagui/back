package com.example.parameterization.Repository;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Enum.MedicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MedicationRepo extends JpaRepository<Medication, Integer> {
    boolean existsByMedicationNameOrMedicationCode(String medicationName, String medicationCode);
    boolean existsByMedicationNameOrMedicationCodeAndMedicationKyNot(String medicationName, String medicationCode, Integer medicationId);

    List<Medication> findByMedicationNameContaining(String name);

    List<Medication> findByMedicationCode(String code);

    List<Medication> findByMedicationType(MedicationType type);
}
