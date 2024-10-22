package com.example.parameterization;

import com.example.parameterization.Entity.Vaccination;
import com.example.parameterization.Implementation.VaccinationServiceImpl;
import com.example.parameterization.Repository.VaccinationRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class VaccinationServiceTest {

    @Mock
    private VaccinationRepo vaccinationRepository;
    private VaccinationServiceImpl vaccinationUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vaccinationUnderTest = new VaccinationServiceImpl(vaccinationRepository);
    }


    @Test
    void create() {
        // Arrange
        Vaccination vaccinationToCreate = new Vaccination();
        // Act
        vaccinationUnderTest.create(vaccinationToCreate);
        // Assert
        ArgumentCaptor<Vaccination> vaccinationArgumentCaptor = ArgumentCaptor.forClass(Vaccination.class);

        verify(vaccinationRepository).save(vaccinationArgumentCaptor.capture());

        Vaccination capturedVaccination = vaccinationArgumentCaptor.getValue();

        assertEquals(capturedVaccination,vaccinationToCreate);

    }

    @Test
    void retrieveVaccinations() {
        //when
        vaccinationUnderTest.retrieveVaccinations();
        //then
        verify(vaccinationRepository).findAll();
    }

    @Test
    void getVaccinationById() {
        Long id = 1L;
        Vaccination expectedVaccination = new Vaccination();
        when(vaccinationRepository.findById(id)).thenReturn(Optional.of(expectedVaccination));

        // Act
        Optional<Vaccination> actualVaccination = vaccinationUnderTest.getVaccinationById(id);

        // Assert
        assertEquals(expectedVaccination, actualVaccination.orElse(null));
    }

    @Test
    void updateVaccination() {
        // Arrange
        Long idVaccination = 1L;
        Vaccination existingVaccination = new Vaccination(); // create an existing Vaccination object
        existingVaccination.setIdVaccination(idVaccination);

        Vaccination updatedVaccination = new Vaccination(); // create an updated Vaccination object
        updatedVaccination.setIdVaccination(idVaccination);
        updatedVaccination.setVaccineLabel("Updated Label"); // modify some fields

        when(vaccinationRepository.save(any(Vaccination.class))).thenReturn(updatedVaccination);

        // Act
        vaccinationUnderTest.update(updatedVaccination);

        // Assert
        verify(vaccinationRepository).save(updatedVaccination);

        // Additional assertions if needed
        assertEquals("Updated Label", updatedVaccination.getVaccineLabel());
        // Add more assertions based on your requirements
    }


    @Test
    void delete() {
        //arrange
        Long id =1L;
        //act
        vaccinationUnderTest.delete(id);
        //verify delete()of the repo was called with the correct id
        verify(vaccinationRepository,times(1)).deleteById(id);
    }

    @Test
    public void testRetrieveVaccinationByCriteria() {
        // Given
        String criteria = "yourCriteria";

        Vaccination vaccination = new Vaccination();
        vaccination.setVaccineLabel("Vaccine with yourCriteria");

        List<Vaccination> expectedVaccinations = Collections.singletonList(vaccination);

        // Mock the behavior of the repository
        when(vaccinationRepository.findByVaccineLabelContaining(criteria))
                .thenReturn(expectedVaccinations);

        // When
        List<Vaccination> actualVaccinations = vaccinationUnderTest.retrieveVaccinationByCriteria(criteria);

        // Then
        assertEquals(expectedVaccinations, actualVaccinations);
    }
}
