package pro.app.recipeapp.model;

import lombok.Data;

import java.util.List;

@Data
public class Recipe {

    private String title;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> steps;

    public Recipe(String title, int cookingTime, List<Ingredient> ingredients, List<String> steps) {
        this.title = title;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}

