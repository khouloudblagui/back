package com.example.parameterization.Controller;

import com.example.parameterization.Entity.SurgicalProcedure;
import com.example.parameterization.Service.SurgicalProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/procedures")

public class SurgicalProcedureController {
    @Autowired
    private SurgicalProcedureService Iservice;

    @GetMapping
    public ResponseEntity<List<SurgicalProcedure>> getAllProcedures() {
        List<SurgicalProcedure> procedures = Iservice.getAllProcedures();
        return new ResponseEntity<>(procedures, HttpStatus.OK);
    }

    @GetMapping("/{cptCode}")
    public boolean getProcedureByCptCode(@PathVariable("cptCode") String cptCode) {
        SurgicalProcedure procedure = Iservice.getProcedureBycptCode(cptCode);
        return procedure != null; // Retourne true si la procédure est trouvée, sinon false
    }


    @PostMapping("/add")
    public ResponseEntity<SurgicalProcedure> addProcedure(@RequestBody SurgicalProcedure procedure) {
        // Check for duplicate CPT code
        if (Iservice.existsBycptCode(procedure.getCptCode())) {
            // Return a 400 Bad Request response with an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        // If no duplicate found, add the procedure
        SurgicalProcedure addedProcedure = Iservice.addProcedure(procedure);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProcedure);
    }


    @PutMapping("/{id}")
    public SurgicalProcedure updateProcedure(@PathVariable long cptky, @RequestBody SurgicalProcedure procedure){
        return Iservice.updateProcedure(cptky, procedure);
    }
    @DeleteMapping("/delete/{CPT_Ky}")
    public void deleteProcedure(@PathVariable("CPT_Ky") long cptky){
        Iservice.deleteProcedure(cptky);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file){
        this.Iservice.saveSurgicalProcedureToDatabase(file);
        return ResponseEntity
                .ok(Map.of("Message" , " data uploaded and saved to database successfully"));
    }
    @GetMapping("/search")
    public List<SurgicalProcedure> searchProcedures(@RequestParam(required = false) String cptCode) {
        if (cptCode != null && !cptCode.isEmpty()) {
            return Iservice.searchByCptCode(cptCode);
        } else {
            return Iservice.getAllProcedures();
        }
    }

    @PutMapping("/edit/{cptCode}/{newDescription}/{newcategory}")
    public ResponseEntity<String> editsurgicalProcedure(@PathVariable String cptCode,@PathVariable String newDescription, @PathVariable String newcategory) {
        // Appel de la méthode de service pour éditer surgicalProcedure
        Iservice.editsurgicalProcedure(cptCode, newDescription, newcategory);

        // Réponse de succès
        return ResponseEntity.ok("surgicalProcedure avec le code " + cptCode + " a été mis à jour avec succès.");
    }


}



