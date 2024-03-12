package com.example.parameterization.Repository;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Enum.MedicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MedicationRepo extends JpaRepository<Medication, Integer> {
    boolean existsByMedicationNameOrMedicationCode(String imedicationName, String imedicationCode);
    boolean existsByMedicationNameOrMedicationCodeAndMedicationKyNot(String imedicationName, String imedicationCode, Integer imedicationId);

    List<Medication> findByMedicationNameContaining(String iname);

    List<Medication> findByMedicationCode(String icode);

    List<Medication> findByMedicationType(MedicationType itype);
}
