package com.example.parameterization.Entity;

import com.example.parameterization.Enum.ElementType;
import com.example.parameterization.Enum.TemplateFormat;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "DocTemp")
public class DocTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TemplateDoc_Ky;

    @Column(name = "TemplateDoc_Title", nullable = false, unique = true)
    private String templateDocTitle;

    private String TemplateDoc_Desc;

    @Enumerated(EnumType.ORDINAL)
    private TemplateFormat TemplateDoc_Format;

    @Lob
    private byte[] fileData;;

    @Enumerated(EnumType.ORDINAL)
    private ElementType TemplateDoc_PrntElmntTp;

    private int TemplateDoc_PrntElmntKy;

}

