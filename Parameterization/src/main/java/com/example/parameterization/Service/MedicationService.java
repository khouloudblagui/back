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

    public boolean medicationExists(String medicationName, String medicationCode) {
        return MRepo.existsByMedicationNameOrMedicationCode(medicationName, medicationCode);
    }

    public boolean medicationExistsExcludingId(String medicationName, String medicationCode, Integer medicationId) {
        return MRepo.existsByMedicationNameOrMedicationCodeAndMedicationKyNot(medicationName, medicationCode, medicationId);
    }

    public void saveorUpdate(Medication medications) {

        MRepo.save(medications);
    }

    public void delete(Integer Medication_Ky) {

        MRepo.deleteById(Medication_Ky);
    }

    public Medication getmedicationById(Integer Medication_Ky) {
        return MRepo.findById(Medication_Ky).get();
    }


    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    // Méthode pour récupérer les médicaments à partir d'un fichier Excel
    public static List<Medication> getMedicationsDataFromExcel(InputStream inputStream) {
        List<Medication> medications = new ArrayList<>();
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
                Medication medication = new Medication();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 1 -> medication.setMedicationCode(cell.getStringCellValue());
                        case 2 -> medication.setMedicationName(cell.getStringCellValue());
                        case 3 -> medication.setMedicationType(MedicationType.values()[(int) cell.getNumericCellValue()]);
                        case 4 -> medication.setMedicationStrength(MedicationStrength.values()[(int) cell.getNumericCellValue()]);
                        case 5 -> medication.setMedicationDosageForm(DosageForm.values()[(int) cell.getNumericCellValue()]);
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                medications.add(medication);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medications;
    }

    // Méthode pour sauvegarder un fichier de médicaments
    public void savemedicationfile(MultipartFile file) {
        if (isValidExcelFile(file)) {
            try {
                List<Medication> medications = getMedicationsDataFromExcel(file.getInputStream());
                this.MRepo.saveAll(medications);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public List<Medication> searchByName(String name) {
        return MRepo.findByMedicationNameContaining(name);
    }

    public List<Medication> searchByCode(String code) {
        return MRepo.findByMedicationCode(code);
    }

    public List<Medication> searchByType(MedicationType type) {
        return MRepo.findByMedicationType(type);
    }
        // Méthode pour obtenir tous les médicaments
        public List<Medication> getMedications () {
            return MRepo.findAll();
        }
        public Medication findById (Integer id){
            return MRepo.findById(id).orElse(null);
        }

    }


