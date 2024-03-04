package com.example.parameterization.Service;

import com.example.parameterization.Entity.SurgicalProcedure;
import com.example.parameterization.Repository.SurgicalProcedureRepository;
import jakarta.ws.rs.NotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class SurgicalProcedureService {
    @Autowired
     SurgicalProcedureRepository repository;
     public List<SurgicalProcedure> getAllProcedures(){
         return repository.findAll();
     }
     public SurgicalProcedure addProcedure(SurgicalProcedure procedure){
         return repository.save(procedure);
     }
     public SurgicalProcedure updateProcedure(long cptky,SurgicalProcedure procedure){
         if (!repository.existsById(cptky)){
             throw new NotFoundException("Procedure not found with id : " + cptky);
     }
       procedure.setCptky(cptky);
         return repository.save(procedure);
     }
     public void deleteProcedure(long cptky){
           if (!repository.existsById(cptky)){
               throw new NotFoundException("procedure not found with id : " + cptky);

           }
           repository.deleteAllById(cptky);
         }

         public SurgicalProcedure getProcedureById(long cptky){
         return repository.findById(cptky).orElseThrow(() -> new NotFoundException("Procedure Not found with id : " + cptky));
         }
    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }
    public static List<SurgicalProcedure> getsurgicalProcedureDataFromExcel(InputStream inputStream){
        List<SurgicalProcedure> surgicalProcedures = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Feuil1");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                SurgicalProcedure surgicalProcedure = new SurgicalProcedure();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 1 -> surgicalProcedure.setCptCode(cell.getStringCellValue());
                        case 2 -> surgicalProcedure.setCptDesc(cell.getStringCellValue());
                        case 3 -> surgicalProcedure.setCptCategory(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                surgicalProcedures.add(surgicalProcedure);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return surgicalProcedures;
    }
    public void saveSurgicalProcedureToDatabase(MultipartFile file){
        if(isValidExcelFile(file)){
            try {
                List<SurgicalProcedure> customers =getsurgicalProcedureDataFromExcel(file.getInputStream());
                this.repository.saveAll(customers);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }


    public List<SurgicalProcedure> searchByCptCode(String cptCode) {
        if (cptCode != null && !cptCode.isEmpty()) {
            return repository.findByCptCodeContainingIgnoreCase(cptCode);
        } else {

            return Collections.emptyList();
        }
    }

}
