package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllergyDTO {

    private Long allergyKy;
    private String allergyName;
    private String allergyType;
    private String allergyDesc;
    private String allergySeverity;
    private String allergySymptoms;
}
