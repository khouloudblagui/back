package org.example.doctor.Service;

import org.example.doctor.Entity.Exam;
import org.example.doctor.Repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getExamsByDoctor(Long doctorId) {
        return examRepository.findByDoctorId(doctorId);
    }

    public List<Exam> getExamsByPatient(Long patientId) {
        return examRepository.findByPatientId(patientId);
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}
