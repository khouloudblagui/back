package com.example.parameterization.Controller;

import com.example.parameterization.Entity.AdverseEffect;
import com.example.parameterization.Service.AdverseEffectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/adverseEffect")
public class AdverseEffectController {

    private final AdverseEffectService adverseEffectService;

    public AdverseEffectController(AdverseEffectService adverseEffectService) {
        this.adverseEffectService = adverseEffectService;
    }

    @PostMapping("/add")
    public ResponseEntity<AdverseEffect> createAdverseEffect(@RequestBody AdverseEffect iAdverseEffect) {
        adverseEffectService.create(iAdverseEffect);
        return new ResponseEntity<>(iAdverseEffect, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdverseEffect>> getAllVaccinations() {
        List<AdverseEffect> aAdverseEffectList = adverseEffectService.retrieveAdverseEffect();
        return new ResponseEntity<>(aAdverseEffectList, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<AdverseEffect> getAdverseEffectById(@PathVariable("id") Long iIdAdverseEffect) {
        Optional<AdverseEffect> aAdverseEffect = adverseEffectService.getAdverseEffectById(iIdAdverseEffect);
        return aAdverseEffect.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdverseEffect> updateVaccination(@PathVariable("id") Long iIdAdverseEffect, @RequestBody AdverseEffect iAdverseEffect) {
        Optional<AdverseEffect> aExistingAdverseEffect = adverseEffectService.getAdverseEffectById(iIdAdverseEffect);
        if (aExistingAdverseEffect.isPresent()) {
            iAdverseEffect.setIdAdverseEffect(iIdAdverseEffect);
            adverseEffectService.update(iAdverseEffect);
            return new ResponseEntity<>(iAdverseEffect, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAdverseEffect(@PathVariable("id") Long iIdAdverseEffect) {
        Optional<AdverseEffect> aExistingAdverseEffect = adverseEffectService.getAdverseEffectById(iIdAdverseEffect);
        if (aExistingAdverseEffect.isPresent()) {
            adverseEffectService.delete(iIdAdverseEffect);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

