package com.example.parameterization.Service;

import com.example.parameterization.Entity.BioAnalyses;

import java.util.List;

public interface BioAnalysesService {
    List<BioAnalyses> getAllBioAnalyses();
    BioAnalyses getBioAnalysesById(long iId);
    BioAnalyses addBioAnalyses(BioAnalyses iBioAnalyses);
    BioAnalyses updateBioAnalyses(BioAnalyses iBioAnalyses);
    void deleteBioAnalyses(long iId);
    List<BioAnalyses> retrieveBioAnalysesByCriteria(String iCriteria);

}
