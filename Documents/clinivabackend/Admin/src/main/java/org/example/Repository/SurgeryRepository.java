package org.example.Repository;

import org.example.Entity.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurgeryRepository extends JpaRepository<Surgery, Long> {
    // Ajoutez des méthodes de recherche personnalisées si nécessaire
}
