package pro.app.recipeapp.services;

import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.model.Recipe;

public interface ValidationService {

    public boolean isValid(Ingredient ingredient);
    public boolean isValid(Recipe recipe);

}
