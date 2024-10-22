package com.example.parameterization;

import com.example.parameterization.Entity.BioAnalyses;
import com.example.parameterization.Implementation.BioAnalysesServiceImpl;
import com.example.parameterization.Repository.BioAnalysesRepo;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BioAnalysesServiceImplTest {
    @Mock
    private BioAnalysesRepo bioAnalysesRepository;

    @InjectMocks
    private BioAnalysesServiceImpl bioAnalysesService;

    @Test
    public void testGetBioAnalysesById() {
        // Arrange
        long id = 1L;
        BioAnalyses expectedBioAnalyses = new BioAnalyses();
        expectedBioAnalyses.setId(id);
        when(bioAnalysesRepository.findById(id)).thenReturn(Optional.of(expectedBioAnalyses));

        // Act
        BioAnalyses actualBioAnalyses = bioAnalysesService.getBioAnalysesById(id);

        // Assert
        assertEquals(expectedBioAnalyses, actualBioAnalyses);
        verify(bioAnalysesRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllBioAnalyses() {
        // Créer une liste simulée d'analyses biologiques
        List<BioAnalyses> mockList = new ArrayList<>();
        mockList.add(new BioAnalyses());
        mockList.add(new BioAnalyses());
        // Simuler le comportement du repository lors de la récupération de toutes les analyses
        when(bioAnalysesRepository.findAll()).thenReturn(mockList);
        // Appeler la méthode du service pour récupérer toutes les analyses biologiques
        List<BioAnalyses> allBioAnalyses = bioAnalysesService.getAllBioAnalyses();
        // Vérifier si la liste n'est pas vide et contient le bon nombre d'analyses
        assertFalse(allBioAnalyses.isEmpty());
        assertEquals(2, allBioAnalyses.size());
    }

    @Test
    public void testAddBioAnalyses() {
        // Créer un objet BioAnalyses simulé
        BioAnalyses bioAnalyses = new BioAnalyses();
        bioAnalyses.setBiologicalAnalysisName("Test Analysis");
        // Simuler le comportement du repository lors de l'ajout
        when(bioAnalysesRepository.save(any(BioAnalyses.class))).thenReturn(bioAnalyses);
        // Appeler la méthode du service pour ajouter une analyse biologique
        BioAnalyses savedBioAnalyses = bioAnalysesService.addBioAnalyses(bioAnalyses);
        // Vérifier si l'analyse biologique a été ajoutée avec succès
        assertNotNull(savedBioAnalyses);
        assertEquals("Test Analysis", savedBioAnalyses.getBiologicalAnalysisName());
    }

    @Test
    public void testUpdateBioAnalyses() {
        // Arrange
        Long bioanalysesid = 1L;
        BioAnalyses existingBioAnalyses = new BioAnalyses(); // create an existing Vaccination object
        existingBioAnalyses.setId(bioanalysesid);

        BioAnalyses updatedBioAnalyses = new BioAnalyses(); // create an updated Vaccination object
        updatedBioAnalyses.setId(bioanalysesid);
        updatedBioAnalyses.setBiologicalAnalysisName("Updated Name"); // modify some fields

        when(bioAnalysesRepository.save(any(BioAnalyses.class))).thenReturn(updatedBioAnalyses);

        // Act
        bioAnalysesService.updateBioAnalyses(updatedBioAnalyses);

        // Assert
        verify(bioAnalysesRepository).save(updatedBioAnalyses);

        // Additional assertions if needed
        assertEquals("Updated Name", updatedBioAnalyses.getBiologicalAnalysisName());
        // Add more assertions based on your requirements

    }

    @Test
    public void testDeleteBioAnalyses() {
        // Définir l'ID de l'analyse biologique à supprimer
        long bioAnalysisId = 1L;
        // Appeler la méthode du service pour supprimer l'analyse biologique
        bioAnalysesService.deleteBioAnalyses(bioAnalysisId);
        // Vérifier si la méthode deleteById du repository a été appelée avec le bon ID
        verify(bioAnalysesRepository, times(1)).deleteById(bioAnalysisId);
    }

    @Test
    public void testRetrieveBioAnalysesByCriteria() {
        // Préparation des données de test
        String criteria = "test";
        List<BioAnalyses> expectedBioAnalysesList = new ArrayList<>();
        expectedBioAnalysesList.add(new BioAnalyses());
        expectedBioAnalysesList.add(new BioAnalyses());

        // Définition du comportement du repository simulé
        when(bioAnalysesRepository.findBybiologicalAnalysisNameContaining(criteria))
                .thenReturn(expectedBioAnalysesList);

        // Exécution de la méthode à tester
        List<BioAnalyses> retrievedBioAnalysesList = bioAnalysesService.retrieveBioAnalysesByCriteria(criteria);

        // Vérification
        assertEquals(expectedBioAnalysesList.size(), retrievedBioAnalysesList.size());
        // Ajoutez d'autres vérifications selon vos besoins
    }


}
