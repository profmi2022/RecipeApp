package pro.app.recipeapp.services;

import pro.app.recipeapp.model.Recipe;

public interface RecipeService {


        Recipe save(Recipe recipe);
        Recipe getById(Long id);

    }
