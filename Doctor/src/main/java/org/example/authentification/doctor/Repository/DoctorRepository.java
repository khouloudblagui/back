package org.example.authentification.doctor.Repository;

import org.example.authentification.doctor.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
