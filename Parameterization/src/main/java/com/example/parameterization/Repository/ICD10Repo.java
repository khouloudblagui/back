package com.example.parameterization.Repository;

import com.example.parameterization.Entity.ICD10;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;
@Repository
public interface ICD10Repo extends JpaRepository<ICD10,Integer> {
    Optional<ICD10> findICD10ByICD10Code(String code) ;

}
