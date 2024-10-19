package com.example.parameterization;

import com.example.parameterization.Entity.SurgicalProcedure;
import com.example.parameterization.Repository.SurgicalProcedureRepository;
import com.example.parameterization.Service.SurgicalProcedureService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SurgicalProcedureServiceTest {
    @Test
    void testIsValidExcelFile() {
        // Test with valid Excel file
        MockMultipartFile validExcelFile = new MockMultipartFile(
                "file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);
        assertTrue(SurgicalProcedureService.isValidExcelFile(validExcelFile));

        // Test with invalid file type
        MockMultipartFile invalidFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", new byte[0]);
        assertFalse(SurgicalProcedureService.isValidExcelFile(invalidFile));
    }




}
