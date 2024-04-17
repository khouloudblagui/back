package com.example.parameterization;

import com.example.parameterization.Entity.DocTemp;
import com.example.parameterization.Enum.ElementType;
import com.example.parameterization.Enum.TemplateFormat;
import com.example.parameterization.Implementation.DocTempServiceImpl;
import com.example.parameterization.Repository.DocTempRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocTempServiceImplTest {


    @Mock
    private DocTempRepo docTempRepository;

    @InjectMocks
    private DocTempServiceImpl docTempService;

    @Test
    void uploadDocument_Success() throws IOException {
        // Prepare test data
        MultipartFile file = new MockMultipartFile("test.txt", "This is a test".getBytes());
        String title = "Test Document";
        String description = "Test Description";
        TemplateFormat format = TemplateFormat.PDF;
        ElementType parentElementType = ElementType.Service;
        int parentElementKey = 123;

        // Call the method under test
        docTempService.uploadDocument(file, title, description, format, parentElementType, parentElementKey);

        // Verify that the repository's save method was called once with the correct arguments
        verify(docTempRepository, times(1)).save(any(DocTemp.class));
    }

    @Test
    void getDocumentById_ReturnsDocTemp() {
        // Prepare test data
        long id = 1L;
        DocTemp expectedDocTemp = new DocTemp();
        expectedDocTemp.setTemplateDocTitle("Test Document");
        expectedDocTemp.setTemplateDoc_Desc("Test Description");

        // Mock the repository method
        when(docTempRepository.findById(id)).thenReturn(Optional.of(expectedDocTemp));

        // Call the method under test
        DocTemp actualDocTemp = docTempService.getDocumentById(id);

        // Verify that the returned DocTemp object matches the expected one
        assertEquals(expectedDocTemp, actualDocTemp);
    }

    // Add similar tests for other methods like getAllDocuments, deleteDocument, and updateDocument

}
