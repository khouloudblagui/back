package com.example.parameterization.Controller;

import com.example.parameterization.Entity.ICD10;
import com.example.parameterization.Service.ICD10Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/icd10")
public class ICD10Controller {
    private final ICD10Service icd10Service;

    public ICD10Controller(ICD10Service icd10Service) {
        this.icd10Service = icd10Service;
    }

    @PostMapping("/saveICD10")
    public ResponseEntity<String> saveICD10(@RequestBody ICD10 icd10) {
        icd10Service.saveICD10(icd10);
        return ResponseEntity.status(HttpStatus.CREATED).body("ICD10 ajouté avec succès.");
    }

    @PostMapping("add/{iCode}/{iDescription}/{iNotes}")
    public ResponseEntity<String> addICD10(@PathVariable String iCode, @PathVariable String iDescription, @PathVariable String iNotes) {
        try {
            icd10Service.AddCode(iCode,iDescription,iNotes);
            return ResponseEntity.status(HttpStatus.CREATED).body("ICD10 ajouté avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de l'ajout de l'ICD10.");
        }
    }


    @DeleteMapping("/delete/{iCode}")
    public ResponseEntity<String> deleteICD10(@PathVariable String iCode) {
        try {
            icd10Service.deleteICD10(iCode);
            return ResponseEntity.status(HttpStatus.OK).body("ICD10 supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de la suppression de l'ICD10.");
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) throws IOException {
        this.icd10Service.saveICD10codesToDatabase(file);
        return ResponseEntity.ok(Map.of("Message", "ICD10Codes data uploaded and saved to database successfully"));
    }




    @PostMapping("/test-excel-validation")
    public ResponseEntity<String> testExcelValidation(@RequestParam("file") MultipartFile file) {
        boolean isValid = icd10Service.isValidExcelFile(file);
        if (isValid) {
            return new ResponseEntity<>("Le fichier est un fichier Excel valide au format XLSX", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Le fichier n'est pas un fichier Excel valide au format XLSX", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/viewICD10Details/{iCode}")
    public Optional<ICD10> viewICD10Details(@PathVariable String iCode) {
        return icd10Service.viewDetailsICD10(iCode);
    }

    @GetMapping
        public ResponseEntity<List<ICD10>> getCustomers(){
            return new ResponseEntity<>(icd10Service.getICD10Codes(), HttpStatus.FOUND);
        }
    }



