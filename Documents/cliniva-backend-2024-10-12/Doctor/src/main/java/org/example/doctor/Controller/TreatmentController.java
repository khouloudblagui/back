package org.example.doctor.Controller;

import org.example.doctor.Entity.Treatment;
import org.example.doctor.Service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/doctor/{doctorId}")
    public List<Treatment> getTreatmentsByDoctor(@PathVariable Long doctorId) {
        return treatmentService.getTreatmentsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Treatment> getTreatmentsByPatient(@PathVariable Long patientId) {
        return treatmentService.getTreatmentsByPatient(patientId);
    }

    @PostMapping
    public Treatment createTreatment(@RequestBody Treatment treatment) {
        return treatmentService.saveTreatment(treatment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable Long id) {
        return treatmentService.getTreatmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }
}
