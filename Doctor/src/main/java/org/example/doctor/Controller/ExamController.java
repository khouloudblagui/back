package org.example.doctor.Controller;

import org.example.doctor.Entity.Exam;
import org.example.doctor.Service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/doctor/{doctorId}")
    public List<Exam> getExamsByDoctor(@PathVariable Long doctorId) {
        return examService.getExamsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Exam> getExamsByPatient(@PathVariable Long patientId) {
        return examService.getExamsByPatient(patientId);
    }

    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {
        return examService.saveExam(exam);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        return examService.getExamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
