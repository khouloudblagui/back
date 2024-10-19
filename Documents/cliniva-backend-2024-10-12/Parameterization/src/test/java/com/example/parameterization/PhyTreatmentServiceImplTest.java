package com.example.parameterization;

import com.example.parameterization.Controller.PhyTreatmentController;
import com.example.parameterization.Entity.PhysicalTreatment;
import com.example.parameterization.Implementation.PhyTreatmentServiceImpl;
import com.example.parameterization.Repository.PhyTreatmentRepo;
import com.example.parameterization.Service.PhyTreatmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PhyTreatmentServiceImplTest {
    @Mock
    private PhyTreatmentRepo phyTreatmentRepository;
    @InjectMocks
    private PhyTreatmentService phyTreatmentService;

    @BeforeEach
    public void setUp() {
        //  MockitoAnnotations.initMocks(this);
        phyTreatmentRepository = mock(PhyTreatmentRepo.class);
        phyTreatmentService = new PhyTreatmentServiceImpl(phyTreatmentRepository);
    }

    @Test
    public void testGetAllTreatments() {
        // Création d'une liste de traitements simulée pour le test
        List<PhysicalTreatment> treatments = Arrays.asList(
                new PhysicalTreatment(1, "Traitement 1", "Description 1", "Durée 1", "Note 1", null),
                new PhysicalTreatment(2, "Traitement 2", "Description 2", "Durée 2", "Note 2", null)
        );

        // Création d'un mock du service de traitement physique
        PhyTreatmentService phyTreatmentService = mock(PhyTreatmentService.class);
        when(phyTreatmentService.getAllTreatments()).thenReturn(treatments);

        // Création de l'instance du contrôleur à tester en lui injectant le mock du service
        PhyTreatmentController controller = new PhyTreatmentController(phyTreatmentService);

        // Appel de la méthode à tester
        ResponseEntity<List<PhysicalTreatment>> responseEntity = controller.getAllTreatments();

        // Vérification de la réponse
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(treatments, responseEntity.getBody());

        // Vérification que la méthode du service a été appelée une fois
        verify(phyTreatmentService, times(1)).getAllTreatments();
    }

    @Test

    public void testGetTreatmetById() {
        // Given
        long id = 1;
        PhysicalTreatment treatment = new PhysicalTreatment();
        treatment.setIdtreatment(id);
        when(phyTreatmentRepository.findById(id)).thenReturn(Optional.of(treatment));

        // When
        PhysicalTreatment result = phyTreatmentService.getTreatmentById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getIdtreatment());


    }

    @Test
    public void testSaveTreatment() {
        // Given
        PhysicalTreatment treatment = new PhysicalTreatment();
        when(phyTreatmentRepository.save(treatment)).thenReturn(treatment);

        // When
        PhysicalTreatment result = phyTreatmentService.saveTreatment(treatment);

        // Then
        assertNotNull(result);
        assertEquals(treatment, result);
    }

    @Test
    public void testUpdateTreatment() {
        // Arrange
        long idTreatment = 1L; // ID du traitement à mettre à jour
        PhysicalTreatment updatedTreatment = new PhysicalTreatment(/* détails du traitement mis à jour */);

        // Mocking the behavior of updateTreatment
        PhyTreatmentService phyTreatmentService = mock(PhyTreatmentService.class);
        when(phyTreatmentService.updateTreatment(any(PhysicalTreatment.class), eq(idTreatment))).thenReturn(updatedTreatment);

        // Création de l'instance du contrôleur à tester en lui injectant le mock du service
        PhyTreatmentController controller = new PhyTreatmentController(phyTreatmentService);

        // Act
        ResponseEntity<PhysicalTreatment> responseEntity = controller.updateTreatment(updatedTreatment, idTreatment);

        // Assert
        // Vérification que la méthode du service a été appelée une fois avec les bons paramètres
        verify(phyTreatmentService, times(1)).updateTreatment(eq(updatedTreatment), eq(idTreatment));

        // Vérification de la réponse
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTreatment, responseEntity.getBody());
    }

    @Test
    public void testDeleteTreatment() {
        // Given
        long id = 1;

        // When
        phyTreatmentService.deleteTreatment(id);

        // Then
        verify(phyTreatmentRepository, times(1)).deleteById(id);
    }

    @Test
    public void testRetrievePhyTreatmentByCriteria() {
        // Given
        String criteria = "test";
        List<PhysicalTreatment> treatments = new ArrayList<>();
        treatments.add(new PhysicalTreatment());
        when(phyTreatmentRepository.findByphyTrNameContaining(criteria)).thenReturn(treatments);

        // When
        List<PhysicalTreatment> result = phyTreatmentService.retrievePhyTreatmentByCriteria(criteria);

        // Then
        assertEquals(1, result.size());
    }

}
