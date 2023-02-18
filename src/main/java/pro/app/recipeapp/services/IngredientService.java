package pro.app.recipeapp.services;

import pro.app.recipeapp.model.Ingredient;


public interface IngredientService {

    Ingredient save(Ingredient ingredient);
    Ingredient getById(Long id);
    Ingredient editById(Long id, Ingredient ingredient);
    Ingredient deleteById(Long id);
    String getAll();
    String getName(Long id);

}
