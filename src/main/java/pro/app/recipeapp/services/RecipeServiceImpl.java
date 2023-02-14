package pro.app.recipeapp.services;

import org.springframework.stereotype.Service;
import pro.app.recipeapp.model.Recipe;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService{

    private static final Map<Long, Recipe> recipeMap = new HashMap<>();
    private static Long idCounter = 1L;

    @Override
    public Recipe save(Recipe recipe) {
        if (recipe != null
                && !recipe.getTitle().isEmpty()
                && !recipe.getIngredients().isEmpty()
                && !recipe.getSteps().isEmpty()) {
            return recipeMap.put(idCounter++, recipe);
        } else {
            return null;
        }
    }

    @Override
    public Recipe getById(Long id) {
        return recipeMap.get(id);
    }
}
