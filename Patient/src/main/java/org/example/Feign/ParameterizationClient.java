package org.example.Feign;

import org.example.DTO.AllergyDTO;
import org.example.DTO.MedicationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "parameterization-service")  // Le nom du service dans Eureka
public interface ParameterizationClient {

    @GetMapping("/allergies/patient/{patientId}")
    List<AllergyDTO> getAllergiesByPatientId(@PathVariable("patientId") Long patientId);

    @GetMapping("/allergies/view/details/{id}")
    AllergyDTO getAllergyById(@PathVariable("id") Long id);

    @GetMapping("/medication")
    List<MedicationDTO> getAllMedications();

    @GetMapping("/medication/{medicationId}")
    MedicationDTO getMedicationById(@PathVariable("medicationId") Integer medicationId);

    @GetMapping("/medication/patient/{patientId}")
    List<MedicationDTO> getMedicationsByPatientId(@PathVariable("patientId") Long patientId);
}
