package com.example.parameterization.Repository;

import com.example.parameterization.Entity.BioAnalyses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BioAnalysesRepo extends JpaRepository<BioAnalyses, Long> {
    List<BioAnalyses> findBybiologicalAnalysisNameContaining(String iCriteria);
}
