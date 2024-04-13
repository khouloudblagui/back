package com.example.parameterization.Repository;

import com.example.parameterization.Entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VaccinationRepo extends JpaRepository<Vaccination, Long> {

    List<Vaccination> findByVaccineLabelContaining(String iCriteria);

}
