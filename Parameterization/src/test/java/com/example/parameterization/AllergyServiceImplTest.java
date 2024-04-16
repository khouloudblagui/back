package com.example.parameterization;

import com.example.parameterization.Entity.Allergy;
import com.example.parameterization.Enum.AllergyType;
import com.example.parameterization.Enum.Severity;
import com.example.parameterization.Implementation.AllergyServiceImpl;
import com.example.parameterization.Repository.AllergyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class AllergyServiceImplTest {

    // Mocking the AllergyRepository:
    // Ce mock sera utilisé pour simuler le comportement du vrai AllergyRepository dans le test.
    @Mock
    private AllergyRepo allergyRepository;
    @Mock
    private SymptomService symptomService;

    // Injecting the mock repository into the service:
    // Cela signifie que lorsque le service utilise allergyRepository, il utilisera le mock plutôt que le véritable AllergyRepository.
    @InjectMocks
    private AllergyServiceImpl allergyService;
    @BeforeEach
    public void setUp() {

    }

    // Setting up the test:
    // Nous initialisons les mocks avant chaque test. C'est une bonne pratique pour s'assurer que les mocks sont prêts à être utilisés avant chaque test.


    // Your test methods will go here
    @Test
    public void testGetAllAllergies() {
        // Arrange
        Allergy allergy1 = new Allergy("Allergy1", AllergyType.HAY_FEVER, "Description1", Severity.MILD, null);
        Allergy allergy2 = new Allergy("Allergy2", AllergyType.FOOD_ALLERGY, "Description2", Severity.SEVERE, null);

        // Mocking the behavior of findAll
        when(allergyRepository.findAll()).thenReturn(Arrays.asList(allergy1, allergy2));

        // Act
        List<Allergy> result = allergyService.getAllAllergies();

        // Assert
        // Verify that findAll method of the repository was called
        verify(allergyRepository, times(1)).findAll();

        // Verify the size and content of the result list
        assertEquals(2, result.size());
        assertEquals("Allergy1", result.get(0).getAllergyName());
        assertEquals("Allergy2", result.get(1).getAllergyName());
    }

    @Test
    public void testAddAllergy() {
        // Arrange
        // Initializing the mocks:
        // Nous créons un objet Allergy fictif pour être utilisé comme entrée pour la méthode à tester.
        Allergy allergyToAdd = new Allergy("TestAllergy", AllergyType.HAY_FEVER, "Test Description", Severity.MILD, null);
        // Mocking the behavior of the save method in the repository:
        //Nous configurons le mock pour simuler le comportement de la méthode save de AllergyRepository. Peu importe l'argument passé à save, il renverra toujours allergyToAdd.
        when(allergyRepository.save(any(Allergy.class))).thenReturn(allergyToAdd);

        // Act
        // Invoking the addAllergy method with the sample Allergy:
        //Nous appelons la méthode que nous testons (addAllergy) avec l'objet Allergy fictif.
        Allergy addedAllergy = allergyService.addAllergy(allergyToAdd);

        // Assert
        // Verifying that the added Allergy is not null:
        //Nous appelons la méthode que nous testons (addAllergy) avec l'objet Allergy fictif.
        assertNotNull(addedAllergy);
        // Verifying that the properties of the added Allergy match the expectations
        assertEquals("TestAllergy", addedAllergy.getAllergyName());
        assertEquals(AllergyType.HAY_FEVER, addedAllergy.getAllergyType());
        assertEquals("Test Description", addedAllergy.getAllergyDesc());
        assertEquals(Severity.MILD, addedAllergy.getAllergySeverity());

        // Verify that the save method of the repository was called
        // Verify that the save method of the repository was called exactly once with any Allergy
        //Nous vérifions que les propriétés de l'objet Allergy retourné correspondent aux valeurs que nous avons définies lors de l'arrangement.
        verify(allergyRepository, times(1)).save(any());
    }

    @Test
    public void testAddAllergyWithMissingAttribute() {
        // Créer un objet Allergy avec un attribut manquant
        Allergy allergyToAdd = new Allergy(null, AllergyType.HAY_FEVER, "Test Description", Severity.MILD, null);

        // Ajouter l'allergie et obtenir le résultat
        Allergy addedAllergy = allergyService.addAllergy(allergyToAdd);

        // Vérifier que l'allergie ajoutée est null en raison de l'attribut manquant
        assertNull(addedAllergy);
    }


    @Test
    public void testAddAllergyWithDifferentTypesAndSeverities() {
        AllergyType[] types = AllergyType.values();
        Severity[] severities = Severity.values();

        for (AllergyType type : types) {
            for (Severity severity : severities) {
                Allergy testAllergy = new Allergy("TestAllergy", type, "TestDesc", severity, null);
                when(allergyRepository.save(any(Allergy.class))).thenReturn(testAllergy);

                Allergy addedAllergy = allergyService.addAllergy(testAllergy);

                assertNotNull(addedAllergy);
                assertEquals(type, addedAllergy.getAllergyType());
                assertEquals(severity, addedAllergy.getAllergySeverity());
            }


        }
    }
    @Test
    public void testUpdateAllergy() {
        // Arrange
        Long allergyKy = 1L;
        Allergy updatedAllergy = new Allergy();
        updatedAllergy.setAllergyKy(allergyKy);
        updatedAllergy.setAllergyName("Updated Allergy Name");

        when(allergyRepository.save(any(Allergy.class))).thenReturn(updatedAllergy);

        // Act
        Allergy result = allergyService.updateAllergy(updatedAllergy);

        // Assert
        verify(allergyRepository, times(1)).save(any(Allergy.class));

        assertEquals(updatedAllergy, result);
        assertEquals("Updated Allergy Name", result.getAllergyName());

    }

    @Test
    public void testViewDetailsWithExistingAllergy() {
        // Arrange
        Long allergyId = 1L;
        Allergy existingAllergy = new Allergy("TestAllergy", AllergyType.HAY_FEVER, "Test Description", Severity.MILD, null);
        when(allergyRepository.findById(allergyId)).thenReturn(java.util.Optional.of(existingAllergy));

        // Act
        java.util.Optional<Allergy> result = allergyService.viewDetails(allergyId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(existingAllergy, result.get());

        // Verify that the findById method of the repository was called
        verify(allergyRepository, times(1)).findById(allergyId);
    }
    @Test
    public void testViewDetailsWithNonExistingAllergy() {
        // Arrange
        Long allergyId = 2L;
        when(allergyRepository.findById(allergyId)).thenReturn(java.util.Optional.empty());

        // Act
        java.util.Optional<Allergy> result = allergyService.viewDetails(allergyId);

        // Assert
        assertFalse(result.isPresent());

        // Verify that the findById method of the repository was called
        verify(allergyRepository, times(1)).findById(allergyId);
    }
    @Test
    public void testRetrieveAllergiesByCriteria() {
        // Arrange
        String criteria = "Test";
        List<Allergy> expectedAllergies = Arrays.asList(
                new Allergy("TestAllergy1", AllergyType.HAY_FEVER, "Test Description", Severity.MILD, null),
                new Allergy("AnotherTestAllergy", AllergyType.FOOD_ALLERGY, "Another Test Description", Severity.SEVERE, null)
        );

        when(allergyRepository.findAllByAllergyNameContaining(criteria)).thenReturn(expectedAllergies);

        // Act
        List<Allergy> result = allergyService.retrieveAllergiesByCriteria(criteria);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAllergies.size(), result.size());
        assertTrue(result.containsAll(expectedAllergies));

        // Verify that the findAllByAllergyNameContaining method of the repository was called
        verify(allergyRepository, times(1)).findAllByAllergyNameContaining(criteria);
    }
    @Test
    public void testRetrieveAllergiesByCriteriaWithNoMatches() {
        // Arrange
        String criteria = "Nonexistent";
        List<Allergy> expectedAllergies = Arrays.asList();

        when(allergyRepository.findAllByAllergyNameContaining(criteria)).thenReturn(expectedAllergies);

        // Act
        List<Allergy> result = allergyService.retrieveAllergiesByCriteria(criteria);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify that the findAllByAllergyNameContaining method of the repository was called
        verify(allergyRepository, times(1)).findAllByAllergyNameContaining(criteria);
    }
    @Test
    public void testRemoveAllergy() {
        // Arrange
        Long allergyIdToRemove = 1L;

        // Act
        allergyService.removeAllergy(allergyIdToRemove);

        // Assert
        // Verify that the deleteById method of the repository was called with the correct ID
        verify(allergyRepository, times(1)).deleteById(allergyIdToRemove);
    }

}
