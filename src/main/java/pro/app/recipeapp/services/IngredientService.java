package pro.app.recipeapp.services;

import pro.app.recipeapp.model.Ingredient;

public interface IngredientService {

    Ingredient save(Ingredient ingredient);
    Ingredient getById(Long id);






}
