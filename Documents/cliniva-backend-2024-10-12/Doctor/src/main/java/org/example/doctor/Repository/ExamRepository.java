package org.example.doctor.Repository;

import org.example.doctor.Entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByDoctorId(Long doctorId);
    List<Exam> findByPatientId(Long patientId);
}
