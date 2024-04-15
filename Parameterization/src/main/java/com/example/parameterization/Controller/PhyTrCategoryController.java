package com.example.parameterization.Controller;

import com.example.parameterization.Entity.PhysicalTreatmentCategory;
import com.example.parameterization.Service.PhyTrCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/phycategories")
public class PhyTrCategoryController {
    private final PhyTrCategoryService phyTrCategoryService;

    @Autowired
    public PhyTrCategoryController(PhyTrCategoryService phyTrCategoryService) {
        this.phyTrCategoryService = phyTrCategoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhysicalTreatmentCategory>> getAllPhyTrCategories() {
        List<PhysicalTreatmentCategory> phyTrCategories = phyTrCategoryService.getAllPhyTrCategories();
        return ResponseEntity.ok(phyTrCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalTreatmentCategory> getPhyTrCategoryById(@PathVariable("id") long id) {
        PhysicalTreatmentCategory phyTrCategory = phyTrCategoryService.getPhyTrCategoryById(id);
        return ResponseEntity.ok(phyTrCategory);
    }

    @PostMapping("/add")
    public ResponseEntity<PhysicalTreatmentCategory> addPhyTrCategory(@RequestBody PhysicalTreatmentCategory iPhyTrCategory) {
        PhysicalTreatmentCategory createdPhyTrCategory = phyTrCategoryService.addPhyTrCategory(iPhyTrCategory);
        return new ResponseEntity<>(createdPhyTrCategory, HttpStatus.CREATED);}

    @PutMapping("/update/{id}")
    public PhysicalTreatmentCategory updatePhyTrCategory(@RequestBody PhysicalTreatmentCategory iPhyTrCategory, @PathVariable ("id") Integer categoryid ) {
        return this.phyTrCategoryService.updatePhyTrCategory(iPhyTrCategory, categoryid);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhyTrCategory(@PathVariable("id") long iId) {
        phyTrCategoryService.deletePhyTrCategory(iId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PhysicalTreatmentCategory>> retrievePhyTrCategoryByCriteria(@RequestParam("criteria") String criteria) {
        List<PhysicalTreatmentCategory> phyTrCategories = phyTrCategoryService.retrievePhyTrCategoryByCriteria(criteria);
        return ResponseEntity.ok(phyTrCategories);
    }

}
