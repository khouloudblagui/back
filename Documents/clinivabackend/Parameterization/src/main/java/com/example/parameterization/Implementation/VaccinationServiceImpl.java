package com.example.parameterization.Implementation;

import com.example.parameterization.Entity.Vaccination;
import com.example.parameterization.Repository.VaccinationRepo;
import com.example.parameterization.Service.VaccinationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccinationServiceImpl implements VaccinationService {
    private final VaccinationRepo vaccinationRepository;

    public VaccinationServiceImpl(VaccinationRepo vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @Override
    public void create(Vaccination iVaccination) {
        this.vaccinationRepository.save(iVaccination);
    }

    @Override
    public List<Vaccination> retrieveVaccinations() {
        return this.vaccinationRepository.findAll();
    }

    @Override
    public Optional<Vaccination> getVaccinationById(Long iIdVaccination) {
        return this.vaccinationRepository.findById(iIdVaccination);
    }

    @Override
    public void update(Vaccination iVaccination) {
        this.vaccinationRepository.save(iVaccination);
    }

    @Override
    public void delete(Long iIdVaccination) {
        this.vaccinationRepository.deleteById(iIdVaccination);
    }

    @Override
    public List<Vaccination> retrieveVaccinationByCriteria(String iCriteria) {
        return vaccinationRepository.findByVaccineLabelContaining(iCriteria);
    }


}
