package com.example.parameterization.Service;
import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IngredientService {

    @Autowired
    private final IngredientRepo IRepo;

    public void saveorUpdate(Ingredient ingredients) {

        IRepo.save(ingredients);
    }
    public void delete(Integer ingredient_ky) {

        IRepo.deleteById(ingredient_ky);
    }

    public Ingredient getingredientById(Integer ingredient_ky) {
        return IRepo.findById(ingredient_ky).get();
    }


}
