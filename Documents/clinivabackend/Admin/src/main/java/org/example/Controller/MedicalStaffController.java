package org.example.Controller;

import org.example.Entity.MedicalStaff;
import org.example.Service.MedicalStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/staff")
public class MedicalStaffController {

    @Autowired
    private MedicalStaffService medicalStaffService;

    @PostMapping
    public ResponseEntity<MedicalStaff> createMedicalStaff(@RequestBody MedicalStaff staff) {
        return ResponseEntity.ok(medicalStaffService.saveMedicalStaff(staff));
    }

    @GetMapping
    public ResponseEntity<List<MedicalStaff>> getAllMedicalStaff() {
        return ResponseEntity.ok(medicalStaffService.getAllMedicalStaff());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalStaff> getMedicalStaffById(@PathVariable Long id) {
        return medicalStaffService.getMedicalStaffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalStaff(@PathVariable Long id) {
        medicalStaffService.deleteMedicalStaff(id);
        return ResponseEntity.noContent().build();
    }
}
