package org.example.Service;

import org.example.Entity.MedicalStaff;
import org.example.Repository.MedicalStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalStaffService {

    @Autowired
    private MedicalStaffRepository medicalStaffRepository;

    public MedicalStaff saveMedicalStaff(MedicalStaff staff) {
        return medicalStaffRepository.save(staff);
    }

    public List<MedicalStaff> getAllMedicalStaff() {
        return medicalStaffRepository.findAll();
    }

    public Optional<MedicalStaff> getMedicalStaffById(Long id) {
        return medicalStaffRepository.findById(id);
    }

    public void deleteMedicalStaff(Long id) {
        medicalStaffRepository.deleteById(id);
    }
}
