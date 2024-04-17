package com.example.parameterization.Controller;

import com.example.parameterization.Entity.BioAnalyses;
import com.example.parameterization.Service.BioAnalysesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bioanalyses")
public class AnalyseController {

    private final BioAnalysesService bioAnalysesService;

    @Autowired
    public AnalyseController(BioAnalysesService bioAnalysesService) {
        this.bioAnalysesService = bioAnalysesService;
    }

    @GetMapping("/all")
    public List<BioAnalyses> getAllBioAnalyses() {
        return bioAnalysesService.getAllBioAnalyses();
    }

    @GetMapping("/{id}")
    public BioAnalyses getBioAnalysesById(@PathVariable("id") long iId) {
        return bioAnalysesService.getBioAnalysesById(iId);
    }


    //Add Biological Analysis
    @PostMapping("/add")
    public BioAnalyses addBioAnalyses(@RequestBody BioAnalyses iBioAnalyses) {
        return bioAnalysesService.addBioAnalyses(iBioAnalyses);
    }

    //public ResponseEntity<String> addBiologicalAnalysis(@RequestBody BioAnalyses analyses) {
    //    analyseRepository.save(analyses);
    //  return ResponseEntity.status(HttpStatus.CREATED).body("Biological analyses addes successufully");
    //  }

    //update biological analyses
    @PutMapping("/update/{id}")
    public ResponseEntity<BioAnalyses> updateAnalyses(@PathVariable("id") Long iId, @RequestBody BioAnalyses iBioAnalyses) {
        Optional<BioAnalyses> aExistingVaccination = Optional.ofNullable(bioAnalysesService.getBioAnalysesById(iId));
        if (aExistingVaccination.isPresent()) {
            iBioAnalyses.setId(iId);
            bioAnalysesService.updateBioAnalyses(iBioAnalyses);
            return new ResponseEntity<>(iBioAnalyses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //Delete Biological Analysis
    @DeleteMapping("/delete/{id}")
    public void deleteBioAnalyses(@PathVariable("id") long iId) {
        bioAnalysesService.deleteBioAnalyses(iId);
    }


    //Search Biological Analysis
    @GetMapping("/search")
    public ResponseEntity<List<BioAnalyses>> retrieveBioAnalysesByCriteria(@RequestParam("criteria") String iCriteria) {
        List<BioAnalyses> iBioAnalyses = bioAnalysesService.retrieveBioAnalysesByCriteria(iCriteria);
        return new ResponseEntity<>(iBioAnalyses, HttpStatus.OK);
    }

    // @GetMapping("/search")
    //public ResponseEntity<List<BioAnalyses>> retrieveBioAnalysesByCriteria(@RequestParam String criteria) {
    //   List<BioAnalyses> analyses = bioAnalysesService.retrieveBioAnalysesByCriteria(criteria);
    //   return ResponseEntity.ok(analyses);
    // }




}

