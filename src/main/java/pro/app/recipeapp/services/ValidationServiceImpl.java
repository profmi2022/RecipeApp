package pro.app.recipeapp.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.model.Recipe;
@Service
public class ValidationServiceImpl implements ValidationService{

    @Override
    public boolean isValid(Ingredient ingredient) {
        return (ingredient != null
                && !StringUtils.isEmpty(ingredient.getName()));
    }

    @Override
    public boolean isValid(Recipe recipe) {
        return  (recipe != null
                && recipe.getIngredients() != null
                && recipe.getSteps() != null
                && !StringUtils.isEmpty(recipe.getTitle())
                && !recipe.getIngredients().isEmpty()
                && !recipe.getSteps().isEmpty());
    }
}
