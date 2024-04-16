package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Allergy;
import com.example.parameterization.Service.AllergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/allergies")

public class AllergyController {
    @Autowired
    private AllergyService allergyService;


    @GetMapping("/all")
    public ResponseEntity<List<Allergy>> getAllAllergies() {
        List<Allergy> aAllergiesList = allergyService.getAllAllergies();
        return new ResponseEntity<>(aAllergiesList, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Allergy> addAllergy(@RequestBody Allergy iAllergy) {
        Allergy aAddedAllergy = allergyService.addAllergy(iAllergy);
        return ResponseEntity.ok(aAddedAllergy);
    }

    @PutMapping("/update/{iAllergyKy}")
    public ResponseEntity<Allergy> updateAllergy(@PathVariable("iAllergyKy") Long iAllergyKy, @RequestBody Allergy iAllergy) {
        Optional<Allergy> aExistingAllergy = allergyService.viewDetails(iAllergyKy);
        if (aExistingAllergy.isPresent()) {
            iAllergy.setAllergyKy(iAllergyKy);
            allergyService.updateAllergy(iAllergy);
            return new ResponseEntity<>(iAllergy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remove/{iAllergyKy}")
    public ResponseEntity<Void> removeAllergy(@PathVariable Long iAllergyKy) {
        allergyService.removeAllergy(iAllergyKy);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Allergy>> retrieveAllergiesByCriteria(@RequestParam String iCriteria) {
        List<Allergy> aAllergiesList = allergyService.retrieveAllergiesByCriteria(iCriteria);
        return ResponseEntity.ok(aAllergiesList);
    }


    @GetMapping("/view/details/{iAllergyKy}")
    public ResponseEntity<Allergy> getAllergyById(@PathVariable Long iAllergyKy) {
        return allergyService.viewDetails(iAllergyKy)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}