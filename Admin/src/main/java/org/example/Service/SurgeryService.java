package org.example.Service;

import org.example.Entity.Surgery;
import org.example.Repository.SurgeryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurgeryService {

    @Autowired
    private SurgeryRepository surgeryRepository;

    public Surgery saveSurgery(Surgery surgery) {
        return surgeryRepository.save(surgery);
    }

    public List<Surgery> getAllSurgeries() {
        return surgeryRepository.findAll();
    }

    public Optional<Surgery> getSurgeryById(Long id) {
        return surgeryRepository.findById(id);
    }

    public void deleteSurgery(Long id) {
        surgeryRepository.deleteById(id);
    }
}
