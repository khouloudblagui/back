package com.example.parameterization.Entity;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Ingredient {

    @Id

    private String Ingredient_Name;
    private String Ingredient_Desc;

    public Ingredient(String ingredient_Name, String ingredient_Desc) {
        Ingredient_Name = ingredient_Name;
        Ingredient_Desc = ingredient_Desc;
    }

    public String getIngredient_Name() {
        return Ingredient_Name;
    }

    public void setIngredient_Name(String ingredient_Name) {
        Ingredient_Name = ingredient_Name;
    }

    public String getIngredient_Desc() {
        return Ingredient_Desc;
    }

    public void setIngredient_Desc(String ingredient_Desc) {
        Ingredient_Desc = ingredient_Desc;
    }
}
