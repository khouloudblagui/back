package org.example.Repository;

import org.example.Entity.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalStaffRepository extends JpaRepository<MedicalStaff, Long> {
    // Ajoutez des méthodes de recherche personnalisées si nécessaire
}
