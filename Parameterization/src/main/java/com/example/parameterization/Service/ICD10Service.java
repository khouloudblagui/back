package com.example.parameterization.Service;
import com.example.parameterization.Entity.ICD10;
import com.example.parameterization.Repository.ICD10Repo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ICD10Service {

    private final ICD10Repo icd10repository;

    public ICD10 saveICD10(ICD10 icd10) {
        icd10repository.save(icd10);
        System.out.println("ICD10 ajouté avec succès.");
        return icd10;
    }

    public void AddCode(String iCode, String iDescription,String iNotes ) {
      if (!icd10repository.findICD10ByICD10Code(iCode).isPresent()) {
          String aChapter = String.valueOf(iCode.charAt(0));
           String aBlock = iCode.substring(1,3);
         String aCategory = String.valueOf(iCode.charAt(3));
         String aSubcategory = iCode.substring(4,6);
         String AExtension = String.valueOf(iCode.charAt(iCode.length()-1));
            ICD10 icd10 = new ICD10();
            icd10.setICD10Code(iCode);
            icd10.setICD10Description(iDescription);
          icd10.setICD10Category(aCategory);
          icd10.setICD10Block(aBlock);
            icd10.setICD10Chapter(aChapter);
          icd10.setICD10Extension(AExtension);
          icd10.setICD10Subcategory(aSubcategory);
          icd10.setICD10Notes(iNotes);
            icd10repository.save(icd10);
            System.out.println("ICD10 ajouté avec succès.");
        } else {
            System.out.println("ICD10 avec le code " + iCode + " existe dans la base.");
        }

    }

    public void editICD10(String iCode, String newDescription, String newNotes) {
        // Vérifier si le code ICD10 existe dans la base de données
        Optional<ICD10> existingICD10 = icd10repository.findICD10ByICD10Code(iCode);
        if (existingICD10.isPresent()) {
            // Récupérer l'objet ICD10 existant
            ICD10 icd10 = existingICD10.get();

            // Mettre à jour les champs nécessaires
            icd10.setICD10Description(newDescription);
            icd10.setICD10Notes(newNotes);

            // Sauvegarder les modifications dans la base de données
            icd10repository.save(icd10);

            System.out.println("ICD10 avec le code " + iCode + " a été mis à jour avec succès.");
        } else {
            System.out.println("Aucun ICD10 trouvé avec le code " + iCode + ".");
        }
    }

    public void deleteICD10(String iCode) {
        Optional<ICD10> icd10 = icd10repository.findICD10ByICD10Code(iCode);
        if (icd10.isPresent()) {
            icd10repository.delete(icd10.get());
            System.out.println("ICD10 supprimé avec succès.");
        } else {
            System.out.println("ICD10 avec le code " + iCode + " introuvable.");
        }
    }


    public Optional<ICD10> viewDetailsICD10(String iCode) {
        Optional<ICD10> icd10 = icd10repository.findICD10ByICD10Code(iCode);
        if (!icd10.isPresent()) {
            System.out.println("ICD10 avec le code " + iCode + " introuvable.");
        } else {
            System.out.println("ICD10 trouvé avec succès  voici les details associés a ce code  : " + icd10.get());
        }
        return icd10;
    }





    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }



    public static List<ICD10> getICD10codesDataFromExcel(InputStream inputStream) {
        List<ICD10> ICD10codes = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("ICD10CODE");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                ICD10 icd10 = new ICD10();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {

                        case 1 ->
                            icd10.setICD10Code(cell.getStringCellValue());
//                            icd10.setICD10Chapter(String.valueOf(cell.getStringCellValue().charAt(0)));
//                            icd10.setICD10Extension(String.valueOf(cell.getStringCellValue().charAt(cell.getStringCellValue().length() - 1)));
//                            icd10.setICD10Category(String.valueOf(cell.getStringCellValue().charAt(3)));
//                            icd10.setICD10Subcategory(String.valueOf(cell.getStringCellValue().substring(4, 6)));
//                            icd10.setICD10Block(String.valueOf(cell.getStringCellValue().substring(1, 3)));


                        case 2 -> icd10.setICD10Description(cell.getStringCellValue());

                        default -> {
                        }
                    }
                    cellIndex++;
                }
                ICD10codes.add(icd10);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return ICD10codes;
    }



    public void saveICD10codesToDatabase(MultipartFile file) throws IOException {
        if (isValidExcelFile(file)) {
            try{
            List<ICD10> icd10s = getICD10codesDataFromExcel(file.getInputStream());
            icd10repository.saveAll(icd10s);
              }
        catch(IOException e){
                throw new IllegalArgumentException("The file  is not a valid excel file ");
           }

        }
    }



    //chercher le code dans la base
    public Optional<ICD10> getIcd10ByCode(String icd10Code) {
        return icd10repository.findICD10ByICD10Code(icd10Code);
    }

    public List<ICD10> getICD10Codes() {
        return icd10repository.findAll();
    }


}
