package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Service.MedicationService;
import com.example.parameterization.dto.MedicationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.parameterization.Repository.IngredientRepo;

import java.util.List;
import java.util.Map;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/medication")
public class MedicationController {

    @Autowired
    private MedicationService MSer;
    private final IngredientRepo ingredientRepo;

    public MedicationController(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    //Upload
    @PostMapping("/upload-data")
    public ResponseEntity<?> uploadMedicationsData(@RequestParam("file") MultipartFile ifile) {
        if (ifile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Empty file uploaded."));
        }

        if (!MSer.isValidExcelFile(ifile)) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Invalid file format. Please upload a valid Excel file."));
        }

        try {
            List<Medication> medications = MSer.getMedicationsDataFromExcel(ifile.getInputStream(), ingredientRepo);

            for (Medication medication : medications) {

                if (MSer.medicationExists(medication.getMedicationName(), medication.getMedicationCode())) {
                    return ResponseEntity.badRequest().body(Map.of("Message", "Medication '" + medication.getMedicationName() + "' or code '" + medication.getMedicationCode() + "' already exists."));
                }
            }


            MSer.savemedicationfile(ifile);

            return ResponseEntity.ok(Map.of("Message" , "Medications data uploaded and saved to database successfully"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("Message" , "Error processing file."));
        }
    }

    //GetAll
    @GetMapping
    public List<MedicationResponse> getMedications(){
        return MSer.getMedicationResponses();
    }

    //Add
    @PostMapping(value="/add")
    public String saveMedication(@RequestBody Medication imedications) {
        boolean aexists = MSer.medicationExists(imedications.getMedicationName(), imedications.getMedicationCode());
        if (aexists) {
            return "The medication already exist";
        }
        MSer.saveorUpdate(imedications);
        return "Medication added successfully, ID : " + imedications.getMedicationKy();
    }

    //update
    @PutMapping(value="/edit/{Medication_Ky}")
    public Medication updateMedication(@RequestBody Medication imedication, @PathVariable(name="Medication_Ky") Integer iMedication_Ky) {


        Medication existingMedication = MSer.findById(iMedication_Ky);

        // Mettre à jour les attributs du médicament existant
        existingMedication.setMedicationName(imedication.getMedicationName());
        existingMedication.setMedicationCode(imedication.getMedicationCode());
        existingMedication.setMedicationType(imedication.getMedicationType());
        existingMedication.setMedicationStrength(imedication.getMedicationStrength());
        existingMedication.setMedicationDosageForm(imedication.getMedicationDosageForm());

        // Mettre à jour les liens des ingrédients avec le médicament existant
        MSer.updateIngredientLinks(existingMedication, imedication.getMedicIngredientLinks());

        MSer.saveorUpdate(existingMedication);

        return existingMedication ;
    }


    //Delete
    @DeleteMapping("/delete/{Medication_Ky}")
    private void deleteMedication(@PathVariable("Medication_Ky")Integer iMedication_Ky)
    {
        MSer.delete(iMedication_Ky);
    }

    //search medication
    @RequestMapping("/search/{medication_name}")
    private List<MedicationResponse> getMedication(@PathVariable(name="medication_name")String imedication_name)
    {
        return MSer.searchByName(imedication_name);
    }
    //exist
    @GetMapping("/exists")
    public boolean checkIfMedicationExists(@RequestParam String medicationName, @RequestParam String medicationCode) {
        return MSer.medicationExists(medicationName, medicationCode);
    }


}
