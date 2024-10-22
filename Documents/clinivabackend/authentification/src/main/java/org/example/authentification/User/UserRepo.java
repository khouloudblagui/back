package org.example.authentification.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    /* List<User> findAllByStatus(Status status);*/

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllDoctor(@Param("role") Role role);
}
