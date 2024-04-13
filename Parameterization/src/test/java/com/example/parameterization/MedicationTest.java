package com.example.parameterization;

import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Enum.DosageForm;
import com.example.parameterization.Enum.MedicationStrength;
import com.example.parameterization.Enum.MedicationType;
import com.example.parameterization.Repository.MedicationRepo;
import com.example.parameterization.Service.MedicationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationTest {
    @Autowired
    private MedicationService Mser;

    @MockBean
    private MedicationRepo MRepo;

    @Test
    void SaveMedicationTest() {
        Medication medication = new Medication();
        medication.setMedicationCode("MED013");
        medication.setMedicationName("Dermaline");
        medication.setMedicationType(MedicationType.OINTMENT);
        medication.setMedicationStrength(MedicationStrength.STRENGTH_1_PERCENT);
        medication.setMedicationDosageForm(DosageForm.TOPICAL);

        // Spécifier le comportement attendu de la méthode save
        when(MRepo.save(medication)).thenReturn(medication);

        Mser.saveorUpdate(medication);

        // Vérifier que la méthode save a été appelée avec l'objet medication comme argument
        verify(MRepo, times(1)).save(medication);
    }

}
