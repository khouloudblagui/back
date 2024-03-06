package com.example.parameterization.Service;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Enum.DosageForm;
import com.example.parameterization.Enum.MedicationStrength;
import com.example.parameterization.Enum.MedicationType;
import com.example.parameterization.Repository.MedicationRepo;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@AllArgsConstructor
@Service
public class MedicationService {

    private final MedicationRepo MRepo;

    public boolean medicationExists(String imedicationName, String imedicationCode) {
        return MRepo.existsByMedicationNameOrMedicationCode(imedicationName, imedicationCode);
    }

    public boolean medicationExistsExcludingId(String imedicationName, String imedicationCode, Integer imedicationId) {
        return MRepo.existsByMedicationNameOrMedicationCodeAndMedicationKyNot(imedicationName, imedicationCode, imedicationId);
    }

    public void saveorUpdate(Medication imedications) {

        MRepo.save(imedications);
    }

    public void delete(Integer iMedication_Ky) {

        MRepo.deleteById(iMedication_Ky);
    }

    public Medication getmedicationById(Integer iMedication_Ky) {
        return MRepo.findById(iMedication_Ky).get();
    }


    public static boolean isValidExcelFile(MultipartFile ifile) {
        return Objects.equals(ifile.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    // Méthode pour récupérer les médicaments à partir d'un fichier Excel
    public static List<Medication> getMedicationsDataFromExcel(InputStream inputStream) {
        List<Medication> amedications = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("medications");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Medication amedication = new Medication();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 1 -> amedication.setMedicationCode(cell.getStringCellValue());
                        case 2 -> amedication.setMedicationName(cell.getStringCellValue());
                        case 3 -> amedication.setMedicationType(MedicationType.values()[(int) cell.getNumericCellValue()]);
                        case 4 -> amedication.setMedicationStrength(MedicationStrength.values()[(int) cell.getNumericCellValue()]);
                        case 5 -> amedication.setMedicationDosageForm(DosageForm.values()[(int) cell.getNumericCellValue()]);
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                amedications.add(amedication);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return amedications;
    }

    // Méthode pour sauvegarder un fichier de médicaments
    public void savemedicationfile(MultipartFile ifile) {
        if (isValidExcelFile(ifile)) {
            try {
                List<Medication> amedications = getMedicationsDataFromExcel(ifile.getInputStream());
                this.MRepo.saveAll(amedications);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public List<Medication> searchByName(String iname) {
        return MRepo.findByMedicationNameContaining(iname);
    }

    public List<Medication> searchByCode(String icode) {
        return MRepo.findByMedicationCode(icode);
    }

    public List<Medication> searchByType(MedicationType itype) {
        return MRepo.findByMedicationType(itype);
    }
        // Méthode pour obtenir tous les médicaments
        public List<Medication> getMedications () {
            return MRepo.findAll();
        }
        public Medication findById (Integer id){
            return MRepo.findById(id).orElse(null);
        }

    }


