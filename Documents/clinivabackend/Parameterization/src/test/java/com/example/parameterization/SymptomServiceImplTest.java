package com.example.parameterization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SymptomServiceImplTest {
    @Mock
    private SymptomRepo symptomRepository;

    @InjectMocks
    private SymptomServiceImpl symptomService;
    @Test
    public void testGetAllSymptoms() {
        // Arrange
        Symptoms symptom1 = new Symptoms("Symptom1", "Description1", null);
        Symptoms symptom2 = new Symptoms("Symptom2", "Description2", null);

        // Mocking the behavior of findAll
        when(symptomRepository.findAll()).thenReturn(Arrays.asList(symptom1, symptom2));

        // Act
        List<Symptoms> result = symptomService.getAllSymptoms();

        // Assert
        // Verify that findAll method of the repository was called
        verify(symptomRepository, times(1)).findAll();

        // Verify the size and content of the result list
        assertEquals(2, result.size());
        assertEquals("Symptom1", result.get(0).getSymptomName());
        assertEquals("Symptom2", result.get(1).getSymptomName());
    }
    @Test
    public void testAddSymptom() {
        // Arrange
        Symptoms symptomToAdd = new Symptoms("TestSymptom", "Test Description", null);

        // Mocking the behavior of the save method in the repository
        when(symptomRepository.save(any(Symptoms.class))).thenReturn(symptomToAdd);

        // Act
        Symptoms addedSymptom = symptomService.addSymptom(symptomToAdd);

        // Assert
        // Verify that the added Symptom is not null
        assertNotNull(addedSymptom);
        // Verify that the properties of the added Symptom match the expectations
        assertEquals("TestSymptom", addedSymptom.getSymptomName());
        assertEquals("Test Description", addedSymptom.getSymptomDesc());

        // Verify that the save method of the repository was called
        verify(symptomRepository, times(1)).save(any());
    }

    @Test
    public void testRemoveSymptom() {
        // Arrange
        Long symptomIdToRemove = 1L;

        // Act
        symptomService.removeSymptom(symptomIdToRemove);

        // Assert
        // Verify that the deleteById method of the repository was called with the correct ID
        verify(symptomRepository, times(1)).deleteById(symptomIdToRemove);
    }

    @Test
    public void testGetSymptomById() {
        // Arrange
        Long symptomId = 1L;
        Symptoms symptom = new Symptoms("Symptom1", "Description1", null);

        // Mocking the behavior of findById in the repository
        when(symptomRepository.findById(symptomId)).thenReturn(Optional.of(symptom));

        // Act
        Symptoms result = symptomService.getSymptomById(symptomId);

        // Assert
        // Verify that the findById method of the repository was called with the correct ID
        verify(symptomRepository, times(1)).findById(symptomId);

        // Verify that the result is not null and has the expected properties
        assertNotNull(result);
        assertEquals("Symptom1", result.getSymptomName());
        assertEquals("Description1", result.getSymptomDesc());
    }

    @Test
    public void testUpdateSymptom() {
        // Arrange
        Long id = 1L;
        Symptoms existingSymptom = new Symptoms();
        existingSymptom.setSymptomKy(id);
        existingSymptom.setSymptomName("Existing Symptom Name");
        existingSymptom.setSymptomDesc("Existing Symptom Description");

        Symptoms updatedSymptom = new Symptoms();
        updatedSymptom.setSymptomKy(id);
        updatedSymptom.setSymptomName("Updated Symptom Name");
        updatedSymptom.setSymptomDesc("Updated Symptom Description");

        // Configuration du mock pour retourner le symptôme existant lorsqu'il est recherché par ID
        when(symptomRepository.findById(id)).thenReturn(Optional.of(existingSymptom));
        // Configuration du mock pour retourner le symptôme mis à jour après la sauvegarde
        when(symptomRepository.save(existingSymptom)).thenReturn(updatedSymptom);

        // Act
        Symptoms result = symptomService.updateSymptom(id, updatedSymptom);

        // Assert
        // Vérifie que le symptôme a été correctement mis à jour
        assertEquals(updatedSymptom.getSymptomName(), result.getSymptomName());
        assertEquals(updatedSymptom.getSymptomDesc(), result.getSymptomDesc());
    }


}
