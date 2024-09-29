package org.example.Controller;

import org.example.Entity.Surgery;
import org.example.Service.SurgeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/surgeries")
public class SurgeryController {

    @Autowired
    private SurgeryService surgeryService;

    @PostMapping
    public ResponseEntity<Surgery> createSurgery(@RequestBody Surgery surgery) {
        return ResponseEntity.ok(surgeryService.saveSurgery(surgery));
    }

    @GetMapping
    public ResponseEntity<List<Surgery>> getAllSurgeries() {
        return ResponseEntity.ok(surgeryService.getAllSurgeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Surgery> getSurgeryById(@PathVariable Long id) {
        return surgeryService.getSurgeryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurgery(@PathVariable Long id) {
        surgeryService.deleteSurgery(id);
        return ResponseEntity.noContent().build();
    }
}
