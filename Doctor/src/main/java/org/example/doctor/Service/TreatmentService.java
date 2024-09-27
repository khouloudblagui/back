package org.example.doctor.Service;

import org.example.doctor.Entity.Treatment;
import org.example.doctor.Repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    public List<Treatment> getTreatmentsByDoctor(Long doctorId) {
        return treatmentRepository.findByDoctorId(doctorId);
    }

    public List<Treatment> getTreatmentsByPatient(Long patientId) {
        return treatmentRepository.findByPatientId(patientId);
    }

    public Treatment saveTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public Optional<Treatment> getTreatmentById(Long id) {
        return treatmentRepository.findById(id);
    }

    public void deleteTreatment(Long id) {
        treatmentRepository.deleteById(id);
    }
}
