package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Vaccination;
import com.example.parameterization.Service.VaccinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vaccination")
public class VaccinationController {
    private final VaccinationService vaccinationService;

    public VaccinationController(VaccinationService vaccinationService) {
        this.vaccinationService = vaccinationService;
    }

    @PostMapping("/add")
    public ResponseEntity<Vaccination> createVaccination(@RequestBody Vaccination iVaccination) {
        vaccinationService.create(iVaccination);
        return new ResponseEntity<>(iVaccination, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vaccination>> getAllVaccinations() {
        List<Vaccination> aVaccinationsList = vaccinationService.retrieveVaccinations();
        return new ResponseEntity<>(aVaccinationsList, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Vaccination> getVaccinationById(@PathVariable("id") Long iIdVaccination) {
        Optional<Vaccination> aVaccination = vaccinationService.getVaccinationById(iIdVaccination);
        return aVaccination.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vaccination> updateVaccination(@PathVariable("id") Long iIdVaccination, @RequestBody Vaccination iVaccination) {
        Optional<Vaccination> aExistingVaccination = vaccinationService.getVaccinationById(iIdVaccination);
        if (aExistingVaccination.isPresent()) {
            iVaccination.setIdVaccination(iIdVaccination);
            vaccinationService.update(iVaccination);
            return new ResponseEntity<>(iVaccination, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteVaccination(@PathVariable("id") Long iIdVaccination) {
        Optional<Vaccination> aExistingVaccination = vaccinationService.getVaccinationById(iIdVaccination);
        if (aExistingVaccination.isPresent()) {
            vaccinationService.delete(iIdVaccination);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Vaccination>> retrieveVaccinationByCriteria(@RequestParam("criteria") String iCriteria) {
        List<Vaccination> aVaccinationList = vaccinationService.retrieveVaccinationByCriteria(iCriteria);
        return new ResponseEntity<>(aVaccinationList, HttpStatus.OK);
    }

}
