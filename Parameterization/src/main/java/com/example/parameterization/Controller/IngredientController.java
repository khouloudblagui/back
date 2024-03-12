package com.example.parameterization.Controller;
import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService Iser;


    //Add
    @PostMapping(value="/add")
    public Integer saveIngredient(@RequestBody Ingredient ingredients)
    {
        Iser.saveorUpdate(ingredients);
        return ingredients.getIngredientKy();
    }

    //update
    @PutMapping(value="/edit/{ingredient_ky}")
    private Ingredient update(@RequestBody Ingredient ingredient,@PathVariable(name="ingredient_ky")Integer ingredient_ky)
    {
        ingredient.setIngredientKy(ingredient_ky);
        Iser.saveorUpdate(ingredient);
        return ingredient;
    }

    //Delete
    @DeleteMapping("/delete/{ingredient_ky}")
    private void deleteIngredient(@PathVariable("ingredient_ky")Integer ingredient_ky)
    {
        Iser.delete(ingredient_ky);
    }

    //show ingredient
    @RequestMapping("/search/{ingredient_ky}")
    private Ingredient getIngredient(@PathVariable(name="ingredient_ky")Integer ingredient_ky)
    {
        return Iser.getingredientById(ingredient_ky);
    }

}



