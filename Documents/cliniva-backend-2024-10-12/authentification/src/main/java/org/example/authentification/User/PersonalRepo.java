package org.example.authentification.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalRepo extends JpaRepository<Personal,Integer> {
    List<Personal> findAll();

}

