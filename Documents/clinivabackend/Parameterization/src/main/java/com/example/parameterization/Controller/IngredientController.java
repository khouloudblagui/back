package com.example.parameterization.Controller;
import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService Iser;


    //Add
    @PostMapping(value="/add")
    public String saveIngredient(@RequestBody Ingredient ingredients)
    {   boolean aexists = Iser.ingredientExists(ingredients.getIngredientName());
        if (aexists) {
            return "The ingredient already exist";
        }

        Iser.saveorUpdate(ingredients);
        return "Ingredient Added successfully, ID : " + ingredients.getIngredientKy();
    }
    //Upload
    @PostMapping("/upload-data")
    public ResponseEntity<?> uploadIngredientsData(@RequestParam("file") MultipartFile ifile){
        if (ifile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Empty file uploaded."));
        }

        if (!Iser.isValidExcelFile(ifile)) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Invalid file format. Please upload a valid Excel file."));
        }

        try {
            List<Ingredient> ingredients = Iser.getIngredientsDataFromExcel(ifile.getInputStream());

            for (Ingredient ingredient : ingredients) {
                if (Iser.ingredientExists(ingredient.getIngredientName())) {
                    return ResponseEntity.badRequest().body(Map.of("Message", "Ingredient '" + ingredient.getIngredientName() + "' already exists."));
                }
            }
            Iser.saveingredientfile(ifile);
            return ResponseEntity.ok(Map.of("Message" , "Ingredients data uploaded and saved to database successfully"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("Message" , "Error processing file."));
        }
    }

    //update
    @PutMapping(value="/edit/{ingredient_ky}")
    private Ingredient update(@RequestBody Ingredient ingredient,@PathVariable(name="ingredient_ky")Integer ingredient_ky)
    {
        boolean exists = Iser.ingredientExists(ingredient.getIngredientName());
        if (exists) {
            return null;
        }
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

    //search ingredient
    @RequestMapping("/search/{ingredient_name}")
    private Ingredient getIngredient(@PathVariable(name="ingredient_name") String ingredient_name) {
        return Iser.getIngredientByName(ingredient_name);
    }


    //GetAll
    @GetMapping
    public List<Ingredient> getIngredients(){
        return Iser.getIngredients();
    }

    //exist
    @GetMapping("/exists")
    public boolean checkIfIngredientExists(@RequestParam String ingredientName) {
        return Iser.ingredientExists(ingredientName);
    }

}



