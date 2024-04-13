package com.example.parameterization.Service;

import com.example.parameterization.Entity.DocTemp;
import com.example.parameterization.Enum.ElementType;
import com.example.parameterization.Enum.TemplateFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DocTempService {

    void uploadDocument(MultipartFile file, String title, String description, TemplateFormat format,
                        ElementType parentElementType, int parentElementKey) throws IOException;
    DocTemp getDocumentById(Long id);

    List<DocTemp> getAllDocuments();

    void deleteDocument(Long id);

    void updateDocument(Long id, String title, String description, TemplateFormat format,
                        ElementType parentElementType, int parentElementKey) throws IOException;

    byte[] downloadDocument(Long id);

    boolean checkTitleExists(String title);


}
