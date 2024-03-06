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
    public ResponseEntity<?> uploadMedicationsData(@RequestParam("file") MultipartFile ifile){
        this.MSer.savemedicationfile(ifile);
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
    public ResponseEntity<String> saveMedication(@RequestBody Medication imedications) {
        boolean aexists = MSer.medicationExists(imedications.getMedicationName(), imedications.getMedicationCode());
        if (aexists) {
            return ResponseEntity.badRequest().body("Le médicament existe déjà.");
        } else {
            MSer.saveorUpdate(imedications);
            return ResponseEntity.ok("Médicament ajouté avec succès, ID : " + imedications.getMedicationKy());
        }
    }

    //update
    @PutMapping(value="/edit/{Medication_Ky}")
    public ResponseEntity<String> updateMedication(@RequestBody Medication imedication, @PathVariable(name="Medication_Ky") Integer iMedication_Ky) {
        Medication aexistingMedication = MSer.findById(iMedication_Ky);

        if (aexistingMedication == null) {
            return ResponseEntity.notFound().build();
        }

        // Vérifie si le nom ou le code est modifié en une valeur déjà existante
        boolean exists = MSer.medicationExistsExcludingId(imedication.getMedicationName(), imedication.getMedicationCode(), iMedication_Ky);
        if (exists) {
            return ResponseEntity.badRequest().body("Le médicament existe déjà avec ce nom ou ce code.");
        }

        // Met à jour les attributs du médicament existant
        aexistingMedication.setMedicationName(imedication.getMedicationName());
        aexistingMedication.setMedicationCode(imedication.getMedicationCode());
        aexistingMedication.setMedicationType(imedication.getMedicationType());
        aexistingMedication.setMedicationStrength(imedication.getMedicationStrength());
        aexistingMedication.setMedicationDosageForm(imedication.getMedicationDosageForm());
        aexistingMedication.setMedicIngredientLinks(imedication.getMedicIngredientLinks());

        // Enregistre la mise à jour
        MSer.saveorUpdate(aexistingMedication);

        return ResponseEntity.ok("Médicament mis à jour avec succès.");
    }


    //Delete
    @DeleteMapping("/delete/{Medication_Ky}")
    private void deleteMedication(@PathVariable("Medication_Ky")Integer iMedication_Ky)
    {
        MSer.delete(iMedication_Ky);
    }

    //show details medication
    @RequestMapping("/search/{Medication_Ky}")
    private Medication getMedication(@PathVariable(name="Medication_Ky")Integer iMedication_Ky)
    {
        return MSer.getmedicationById(iMedication_Ky);
    }
    //search par criteria
    @GetMapping("/search")
    public List<Medication> searchMedications(
            @RequestParam(required = false) String iname,
            @RequestParam(required = false) String icode,
            @RequestParam(required = false) MedicationType itype) {

        if (iname != null) {
            return MSer.searchByName(iname);
        } else if (icode != null) {
            return MSer.searchByCode(icode);
        } else if (itype != null) {
            return MSer.searchByType(itype);
        } else {
            return MSer.getMedications();
        }
    }
}
