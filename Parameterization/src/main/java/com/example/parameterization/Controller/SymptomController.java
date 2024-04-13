package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Symptoms;
import com.example.parameterization.Service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/symptoms")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    // Retrieve all symptoms
    @GetMapping("/all")
    public List<Symptoms> retrieveAllSymptoms() {
        return symptomService.getAllSymptoms();
    }

    // Add a new symptom
    @PostMapping("/add")
    public Symptoms addSymptom(@RequestBody Symptoms iSymptom) {
        return symptomService.addSymptom(iSymptom);
    }
    // Remove a symptom by ID
    @DeleteMapping("/remove/{iId}")
    public void removeSymptom(@PathVariable Long iId) {
        symptomService.removeSymptom(iId);
    }

    @GetMapping("/{iId}")
    public ResponseEntity<Symptoms> getSymptomById(@PathVariable Long iId) {
        Symptoms aSymptom = symptomService.getSymptomById(iId);
        return ResponseEntity.ok().body(aSymptom);
    }
    // Update a symptom by ID
    @PutMapping("/update/{iId}")
    public ResponseEntity<Symptoms> updateSymptom(@PathVariable Long iId, @RequestBody Symptoms updatedSymptom) {
        Symptoms updated = symptomService.updateSymptom(iId, updatedSymptom);
        if (updated != null) {
            return ResponseEntity.ok().body(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public List<Symptoms> searchSymptomsByCriteria(@RequestParam String criteria) {
        return symptomService.retrieveSymptomsByCriteria(criteria);
    }
}
