package pro.app.recipeapp.services;

import org.springframework.stereotype.Service;
import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.model.Recipe;
@Service
public class ValidationServiceImpl implements ValidationService{

    @Override
    public boolean isValid(Ingredient ingredient) {
        return (ingredient != null
                && ingredient.getName() != null
                && !ingredient.getName().isEmpty());
    }

    @Override
    public boolean isValid(Recipe recipe) {
        return  (recipe != null
                && recipe.getTitle() != null
                && recipe.getIngredients() != null
                && recipe.getSteps() != null
                && !recipe.getTitle().isEmpty()
                && !recipe.getIngredients().isEmpty()
                && !recipe.getSteps().isEmpty());
    }
}
