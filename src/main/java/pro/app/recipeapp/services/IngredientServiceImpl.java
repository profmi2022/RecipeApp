package pro.app.recipeapp.services;

import org.springframework.stereotype.Service;
import pro.app.recipeapp.model.Ingredient;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService{

    private static final Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private static Long idCounter = 1L;

//    public IngredientServiceImpl() {
//        ingredientMap.put(1L, new Ingredient("картофель", 1, "кг"));
//    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (ingredient != null && !ingredient.getName().isEmpty()) {
            return ingredientMap.put(idCounter++, ingredient);
        } else {
            return null;
        }
    }

    @Override
    public Ingredient getById(Long id) {
        return ingredientMap.get(id);
    }
}
