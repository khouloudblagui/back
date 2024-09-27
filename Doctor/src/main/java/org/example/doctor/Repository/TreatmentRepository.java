package org.example.doctor.Repository;

import org.example.doctor.Entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    List<Treatment> findByDoctorId(Long doctorId);
    List<Treatment> findByPatientId(Long patientId);
}
