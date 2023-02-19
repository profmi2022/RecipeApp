package pro.app.recipeapp.services;

import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.model.Recipe;

public interface ValidationService {

    boolean isValid(Ingredient ingredient);
    boolean isValid(Recipe recipe);

}
