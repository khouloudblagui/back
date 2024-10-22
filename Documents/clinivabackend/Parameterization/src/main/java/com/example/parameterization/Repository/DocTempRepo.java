package com.example.parameterization.Repository;

import com.example.parameterization.Entity.DocTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTempRepo extends JpaRepository<DocTemp, Long> {
    boolean existsByTemplateDocTitle(String title);


}