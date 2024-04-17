package com.example.parameterization.Implementation;


import com.example.parameterization.Entity.BioAnalyses;
import com.example.parameterization.Repository.BioAnalysesRepo;
import com.example.parameterization.Service.BioAnalysesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BioAnalysesServiceImpl implements BioAnalysesService {
    private final BioAnalysesRepo bioAnalysesRepository;

    @Autowired
    public BioAnalysesServiceImpl(BioAnalysesRepo bioAnalysesRepository) {
        this.bioAnalysesRepository = bioAnalysesRepository;
    }
    @Override
    public List<BioAnalyses> getAllBioAnalyses(){
        return bioAnalysesRepository.findAll();
    }

    @Override
    public BioAnalyses getBioAnalysesById(long iId) {
        return bioAnalysesRepository.findById(iId).orElseThrow(() -> new RuntimeException("BioAnalysis not found with id :" + iId));
    }
    @Override
    public BioAnalyses addBioAnalyses(BioAnalyses iBioAnalyses) {
        return bioAnalysesRepository.save(iBioAnalyses);
    }

    @Override
    public BioAnalyses updateBioAnalyses(BioAnalyses iBioAnalyses) {
        return this.bioAnalysesRepository.save(iBioAnalyses);
    }


    @Override
    public void deleteBioAnalyses(long iId) {
        bioAnalysesRepository.deleteById(iId);
    }

    @Override
    public List<BioAnalyses> retrieveBioAnalysesByCriteria(String iCriteria) {
        return bioAnalysesRepository.findBybiologicalAnalysisNameContaining(iCriteria);
    }
}

