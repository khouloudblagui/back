package com.example.parameterization.Implementation;

import com.example.parameterization.Entity.PhysicalTreatment;
import com.example.parameterization.Repository.PhyTrCategoryRepo;
import com.example.parameterization.Repository.PhyTreatmentRepo;
import com.example.parameterization.Service.PhyTreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhyTreatmentServiceImpl implements PhyTreatmentService {

    private final PhyTreatmentRepo phyTreatmentRepository;
    @Autowired
    private PhyTrCategoryRepo phyTrCategoryRepository;

    @Autowired
    public PhyTreatmentServiceImpl(PhyTreatmentRepo phyTreatmentRepository) {
        this.phyTreatmentRepository = phyTreatmentRepository;
    }

    @Override
    public List<PhysicalTreatment> getAllTreatments() {
        return phyTreatmentRepository.findAll();
    }

    @Override
    public PhysicalTreatment getTreatmentById(long id) {
        Optional<PhysicalTreatment> treatmentOptional = phyTreatmentRepository.findById(id);
        return treatmentOptional.orElse(null);
    }

    @Override

    public PhysicalTreatment saveTreatment(PhysicalTreatment iTreatment) {
        return phyTreatmentRepository.save(iTreatment);
    }

    @Override
    public PhysicalTreatment updateTreatment(PhysicalTreatment iTreatment, long iId){
        PhysicalTreatment existingPhysicalTreatment = this.phyTreatmentRepository.findById(iId)
                .orElseThrow(() -> new RuntimeException("Physical Treatment not found with id: " + iId));

        // Mettre à jour les champs du traitement
        existingPhysicalTreatment.setPhyTrName(iTreatment.getPhyTrName());
        existingPhysicalTreatment.setPhyTrDesc(iTreatment.getPhyTrDesc());
        existingPhysicalTreatment.setPhyTrDuration(iTreatment.getPhyTrDuration());
        existingPhysicalTreatment.setPhyTrNote(iTreatment.getPhyTrNote());

        // Vérifier si la catégorie de traitement a changé
        if (iTreatment.getPhysicalTreatmentCategory() != null) {
            // Mettre à jour la catégorie de traitement
            existingPhysicalTreatment.setPhysicalTreatmentCategory(iTreatment.getPhysicalTreatmentCategory());
        }

        // Enregistrer les modifications dans la base de données
        return this.phyTreatmentRepository.save(existingPhysicalTreatment);
    }

    @Override
    public void deleteTreatment(long id) {
        phyTreatmentRepository.deleteById(id);
    }

    @Override
    public List<PhysicalTreatment> retrievePhyTreatmentByCriteria(String iiCriteria) {
        // Implementation to retrieve phyTrCategories by criteria if needed
        return phyTreatmentRepository.findByphyTrNameContaining(iiCriteria);
    }
}
