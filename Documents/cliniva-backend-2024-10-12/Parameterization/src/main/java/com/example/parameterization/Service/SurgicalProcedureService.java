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
     SurgicalProcedureRepository Irepository;

    /* public SurgicalProcedure addProcedure(SurgicalProcedure procedure){
         return Irepository.save(procedure);
     }*/
    public SurgicalProcedure addProcedure(SurgicalProcedure procedure) {
        // Check if a procedure with the same cptCode already exists
        if (Irepository.existsBycptCode(procedure.getCptCode())) {
            throw new IllegalArgumentException("Surgical procedure with this CPT code already exists");
        }

        // If no duplicate found, save the procedure
        return Irepository.save(procedure);
    }

    public SurgicalProcedure updateProcedure(long cptky,SurgicalProcedure procedure){
         if (!Irepository.existsById(cptky)){
             throw new NotFoundException("Procedure not found with id : " + cptky);
     }
       procedure.setCptky(cptky);
         return Irepository.save(procedure);
     }
     public void deleteProcedure(long cptky){
           if (!Irepository.existsById(cptky)){
               throw new NotFoundException("procedure not found with id : " + cptky);

           }
           Irepository.deleteById(cptky);
         }
         public List<SurgicalProcedure> getAllProcedures(){
        return Irepository.findAll();
    }
        /* public SurgicalProcedure getProcedureById(long cptky){
         return Irepository.findById(cptky).orElseThrow(() -> new NotFoundException("Procedure Not found with id : " + cptky));
         }*/
        public SurgicalProcedure getProcedureByCptCode(String cptCode) {
            // Ensure that the repository method exists and works as expected
            return Irepository.findBycptCode(cptCode)
                    .orElseThrow(() -> new NotFoundException("Procedure not found with CPT Code: " + cptCode));
        }

    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }
    public static List<SurgicalProcedure> getsurgicalProcedureDataFromExcel(InputStream inputStream){
        List<SurgicalProcedure> asurgicalProcedures = new ArrayList<>();
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
                SurgicalProcedure asurgicalProcedure = new SurgicalProcedure();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 1 -> asurgicalProcedure.setCptCode(cell.getStringCellValue());
                        case 2 -> asurgicalProcedure.setCptDesc(cell.getStringCellValue());
                        case 3 -> asurgicalProcedure.setCptCategory(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                asurgicalProcedures.add(asurgicalProcedure);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return asurgicalProcedures;
    }
    public void saveSurgicalProcedureToDatabase(MultipartFile file){
        if(isValidExcelFile(file)){
            try {
                List<SurgicalProcedure> customers =getsurgicalProcedureDataFromExcel(file.getInputStream());
                this.Irepository.saveAll(customers);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }


    public List<SurgicalProcedure> searchByCptCode(String cptCode) {
        if (cptCode != null && !cptCode.isEmpty()) {
            return Irepository.findByCptCodeContainingIgnoreCase(cptCode);
        } else {

            return Collections.emptyList();
        }
    }





    public boolean existsBycptCode(String cptCode) {
        return Irepository.existsBycptCode(cptCode);
    }

    public SurgicalProcedure getProcedureBycptCode(String cptCode) {
        return Irepository.getProcedureBycptCode(cptCode);
    }
    public void editsurgicalProcedure(String cptCode, String newDescription, String newcategory) {
        // Vérifier si le code surgicalProcedure existe dans la base de données
        Optional<SurgicalProcedure> existingsurgicalProcedure = Irepository.findBycptCode(cptCode);
        if (existingsurgicalProcedure.isPresent()) {
            // Récupérer l'objet surgicalProcedure existant
            SurgicalProcedure surgicalProcedure = existingsurgicalProcedure.get();

            // Mettre à jour les champs nécessaires
            surgicalProcedure.setCptDesc(newDescription);
            surgicalProcedure.setCptCategory(newcategory);

            // Sauvegarder les modifications dans la base de données
            Irepository.save(surgicalProcedure);

            System.out.println("surgicalProcedure avec le code " + cptCode + " a été mis à jour avec succès.");
        } else {
            System.out.println("Aucun surgicalProcedure trouvé avec le code " + cptCode + ".");
        }
    }
}
