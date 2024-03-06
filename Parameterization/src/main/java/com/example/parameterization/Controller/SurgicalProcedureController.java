package com.example.parameterization.Controller;

import com.example.parameterization.Entity.SurgicalProcedure;
import com.example.parameterization.Service.SurgicalProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/procedures")
public class SurgicalProcedureController {
    @Autowired
    private SurgicalProcedureService Iservice;
    @GetMapping
    public List<SurgicalProcedure> getAllProcedures() {
        return Iservice.getAllProcedures();
    }
    @GetMapping ("/{id}")
    public SurgicalProcedure getProcedureById(@PathVariable("id") Long cptky) {
        return Iservice.getProcedureById(cptky);
    }
    @PostMapping ("/add")
    public SurgicalProcedure addProcedure(@RequestBody SurgicalProcedure procedure) {
        return Iservice.addProcedure(procedure);
    }
    @PutMapping("/{id}")
    public SurgicalProcedure updateProcedure(@PathVariable long cptky, @RequestBody SurgicalProcedure procedure){
        return Iservice.updateProcedure(cptky, procedure);
    }
    @DeleteMapping("/{id}")
    public void deleteProcedure(@PathVariable long cptky){
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


}



