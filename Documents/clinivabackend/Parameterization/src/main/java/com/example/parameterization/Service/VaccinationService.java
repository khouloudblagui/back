package com.example.parameterization.Service;

import com.example.parameterization.Entity.Vaccination;

import java.util.List;
import java.util.Optional;

public interface VaccinationService {
    void create(Vaccination iVaccination);

    List<Vaccination> retrieveVaccinations();

    Optional<Vaccination> getVaccinationById(Long iIdVaccination);

    void update(Vaccination iVaccination);

    void delete(Long iIdVaccination);

    List<Vaccination> retrieveVaccinationByCriteria(String iCriteria);

}
