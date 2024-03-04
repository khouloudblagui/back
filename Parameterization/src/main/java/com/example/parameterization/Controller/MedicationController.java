package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Enum.MedicationType;
import com.example.parameterization.Service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/medication")
public class MedicationController {

    @Autowired
    private MedicationService MSer;


    //AddUpload
    @PostMapping("/upload-data")
    public ResponseEntity<?> uploadMedicationsData(@RequestParam("file") MultipartFile file){
        this.MSer.savemedicationfile(file);
        return ResponseEntity
                .ok(Map.of("Message" , " Medications data uploaded and saved to database successfully"));
    }
    //GetAll
    @GetMapping
    public ResponseEntity<List<Medication>> getMedications(){
        return new ResponseEntity<>(MSer.getMedications(), HttpStatus.FOUND);
    }

    //Add
    @PostMapping(value="/add")
    public ResponseEntity<String> saveMedication(@RequestBody Medication medications) {
        boolean exists = MSer.medicationExists(medications.getMedicationName(), medications.getMedicationCode());
        if (exists) {
            return ResponseEntity.badRequest().body("Le médicament existe déjà.");
        } else {
            MSer.saveorUpdate(medications);
            return ResponseEntity.ok("Médicament ajouté avec succès, ID : " + medications.getMedicationKy());
        }
    }

    //update
    @PutMapping(value="/edit/{Medication_Ky}")
    public ResponseEntity<String> updateMedication(@RequestBody Medication medication, @PathVariable(name="Medication_Ky") Integer Medication_Ky) {
        Medication existingMedication = MSer.findById(Medication_Ky);

        if (existingMedication == null) {
            return ResponseEntity.notFound().build();
        }

        // Vérifie si le nom ou le code est modifié en une valeur déjà existante
        boolean exists = MSer.medicationExistsExcludingId(medication.getMedicationName(), medication.getMedicationCode(), Medication_Ky);
        if (exists) {
            return ResponseEntity.badRequest().body("Le médicament existe déjà avec ce nom ou ce code.");
        }

        // Met à jour les attributs du médicament existant
        existingMedication.setMedicationName(medication.getMedicationName());
        existingMedication.setMedicationCode(medication.getMedicationCode());
        existingMedication.setMedicationType(medication.getMedicationType());
        existingMedication.setMedicationStrength(medication.getMedicationStrength());
        existingMedication.setMedicationDosageForm(medication.getMedicationDosageForm());
        existingMedication.setMedicIngredientLinks(medication.getMedicIngredientLinks());

        // Enregistre la mise à jour
        MSer.saveorUpdate(existingMedication);

        return ResponseEntity.ok("Médicament mis à jour avec succès.");
    }


    //Delete
    @DeleteMapping("/delete/{Medication_Ky}")
    private void deleteMedication(@PathVariable("Medication_Ky")Integer Medication_Ky)
    {
        MSer.delete(Medication_Ky);
    }

    //show details medication
    @RequestMapping("/search/{Medication_Ky}")
    private Medication getMedication(@PathVariable(name="Medication_Ky")Integer Medication_Ky)
    {
        return MSer.getmedicationById(Medication_Ky);
    }
    //search par criteria
    @GetMapping("/search")
    public List<Medication> searchMedications(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) MedicationType type) {

        if (name != null) {
            return MSer.searchByName(name);
        } else if (code != null) {
            return MSer.searchByCode(code);
        } else if (type != null) {
            return MSer.searchByType(type);
        } else {
            return MSer.getMedications();
        }
    }
}
