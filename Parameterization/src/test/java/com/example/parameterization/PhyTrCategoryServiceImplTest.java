package com.example.parameterization;

import com.example.parameterization.Entity.PhysicalTreatmentCategory;
import com.example.parameterization.Implementation.PhyTrCategoryServiceImpl;
import com.example.parameterization.Repository.PhyTrCategoryRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhyTrCategoryServiceImplTest {
    @Mock
    private PhyTrCategoryRepo phyTrCategoryRepository;

    @InjectMocks
    private PhyTrCategoryServiceImpl phyTrCategoryService;

    @Test
    public void testGetAllPhyTrCategories(){
        List<PhysicalTreatmentCategory> categories = new ArrayList<>();
        categories.add(new PhysicalTreatmentCategory());
        when(phyTrCategoryRepository.findAll()).thenReturn(categories);

        List<PhysicalTreatmentCategory> result= phyTrCategoryService.getAllPhyTrCategories();

        assertEquals(categories, result);
    }

    @Test
    public void testGetPhyTrCategoryById(){
        PhysicalTreatmentCategory category = new PhysicalTreatmentCategory();
        category.setCategoryid(1);
        when(phyTrCategoryRepository.findById(1L)).thenReturn(Optional.of(category));

        PhysicalTreatmentCategory result= phyTrCategoryService.getPhyTrCategoryById(1);

        assertEquals(category, result);
    }

    @Test
    public void testAddPhyTrCategory(){
        PhysicalTreatmentCategory category=new PhysicalTreatmentCategory();
        when(phyTrCategoryRepository.save(any())).thenReturn(category);

        PhysicalTreatmentCategory result=phyTrCategoryService.addPhyTrCategory(category);

        assertEquals(category, result);
    }

    @Test
    public void testUpdatePhyTrCategory(){
        PhysicalTreatmentCategory category = new PhysicalTreatmentCategory();
        category.setCategoryid(1);
        when(phyTrCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(phyTrCategoryRepository.save(any())).thenReturn(category);

        PhysicalTreatmentCategory updatedCategory = new PhysicalTreatmentCategory();
        updatedCategory.setCategoryid(1);
        updatedCategory.setPhyCategoryName("Updated Name");

        PhysicalTreatmentCategory result = phyTrCategoryService.updatePhyTrCategory(updatedCategory, 1);

        assertEquals("Updated Name", result.getPhyCategoryName());

    }

    @Test
    public void testDeletePhyTrCategory(){
        doNothing().when(phyTrCategoryRepository).deleteById(1L);

        phyTrCategoryService.deletePhyTrCategory(1);

        verify(phyTrCategoryRepository, timeout(1)).deleteById(1L);
    }

    @Test
    public void testRetrievePhyTrCategoryByCriteria(){
        List<PhysicalTreatmentCategory> categories = new ArrayList<>();
        categories.add(new PhysicalTreatmentCategory());
        when(phyTrCategoryRepository.findByphyCategoryNameContaining(any())).thenReturn(categories);

        List<PhysicalTreatmentCategory> result=phyTrCategoryService.retrievePhyTrCategoryByCriteria("criteria");

        assertEquals(categories, result);
    }


}
