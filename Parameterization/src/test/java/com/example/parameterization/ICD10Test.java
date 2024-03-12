package com.example.parameterization;

import com.example.parameterization.Entity.ICD10;
import com.example.parameterization.Repository.ICD10Repo;
import com.example.parameterization.Service.ICD10Service;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ICD10Test {
    @Autowired
    ICD10Service icd10Service ;
    @Test
    @Order(1)
    // Tester que la base de donnees n'est pas vide
    public void testRetreiveALLICDCOdes(){
        List<ICD10> icd10List = icd10Service.getICD10Codes();
        Assertions.assertNotEquals(0,icd10List.size());
    }

   // @Test
    //void testAddCode() {
        // Créer un mock de icd10repository
        ICD10Repo icd10repository = mock(ICD10Repo.class);

        // Créer une instance de ICD10Service avec le mock
       // ICD10Service icd10Service = new ICD10Service(icd10repository);

        // Configuration du mock pour retourner une valeur lorsque la méthode findICD10ByICD10Code est appelée
       // when(icd10repository.findICD10ByICD10Code(anyString())).thenReturn(Optional.empty()); // Simuler qu'aucun code n'existe dans la base de données

        // Appel de la méthode AddCode
        //icd10Service.AddCode("S52516R", "Nondisplaced fracture of unspecified radial styloid process, subsequent encounter for open fracture type IIIA, IIIB, or IIIC with malunion", "Notes");

        // Vérifier si la méthode save a été appelée une fois avec les bons arguments
       // verify(icd10repository, times(1)).save(any(ICD10.class));

        // Vérifier si "ICD10 ajouté avec succès." a été imprimé
        // Vous pouvez modifier cette assertion en fonction de la façon dont vous gérez les sorties de la méthode AddCode
        // Vous pouvez également remplacer la sortie par des assertions sur les exceptions si vous gérez les erreurs de cette manière
        // Par exemple : assertThrows(Exception.class, () -> icd10Service.AddCode("A01", "Cholera", "Notes"));
        // ou utiliser un autre moyen pour capturer les sorties de la méthode
   // }

    @Test
    @Order(2)
        void testGetICD10codesDataFromExcel() {
        // Obtenez un InputStream à partir du fichier Excel de test
        InputStream inputStream = getClass().getResourceAsStream("/ICD10 .xlsx");

        // Appelez la méthode de service pour extraire les codes ICD10 du fichier Excel
        List<ICD10> excelCodes = ICD10Service.getICD10codesDataFromExcel(inputStream);

        //  Extraire les codes ICD10 de la base de données et les stocker dans une liste
        List<ICD10> databaseCodes = icd10Service.getICD10Codes();
        // Comparer les listes de codes ICD10 du fichier Excel et de la base de données
        // Assurez-vous que les listes sont de même taille
        assertEquals(excelCodes.size(), databaseCodes.size(), "Le nombre de codes ICD10 du fichier Excel ne correspond pas au nombre de codes ICD10 de la base de données");

        // Comparer les codes individuels pour s'assurer qu'ils sont identiques
        for (ICD10 excelCode : excelCodes) {
            boolean codeFound = false;
            for (ICD10 databaseCode : databaseCodes) {
                if (excelCode.getICD10Code().equals(databaseCode.getICD10Code())) {
                    // Le code ICD10 a été trouvé dans la base de données

                    assertEquals(excelCode.getICD10Description(), databaseCode.getICD10Description(), "Les descriptions des codes ICD10 ne correspondent pas");
                    codeFound = true;
                    break;
                }
            }
            assertTrue(codeFound, "Le code ICD10 du fichier Excel n'a pas été trouvé dans la base de données");
        }
    }
}





