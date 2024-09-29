package org.example.Repository;

import org.example.Entity.OperationRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRoomRepository extends JpaRepository<OperationRoom, Long> {
    // Ajoutez des méthodes de recherche personnalisées si nécessaire
}
