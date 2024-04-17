package com.example.parameterization.Entity;

import com.example.parameterization.Enum.AnalysisType;
import com.example.parameterization.Enum.MeasurementUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bioanalyses")
public class BioAnalyses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;

    @Column(name = "BiologicalAnalysis_Name", nullable = false)
    private String biologicalAnalysisName;

    @Column(name = "BiologicalAnalysis_Type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AnalysisType biologicalAnalysisType;

    @Column(name = "BiologicalAnalysis_Desc", nullable = false)
    private String biologicalAnalysisDesc;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "BiologicalAnalysis_MeasurmentUnit", nullable = false)
    private MeasurementUnit biologicalAnalysisMeasurmentUnit;

    @Column(name = "BiologicalAnalysis_RefValueMin", nullable = false)
    private Double biologicalAnalysisRefValueMin;

    @Column(name = "BiologicalAnalysis_RefValueMax", nullable = false)
    private Double biologicalAnalysisRefValueMax;
}
