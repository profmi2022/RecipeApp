package pro.app.recipeapp.services;

import org.springframework.stereotype.Service;
import pro.app.recipeapp.model.Ingredient;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService{

    private static final Map<Long, Ingredient> ingredientMap = new TreeMap<>();
    private static Long idCounter = 1L;
    private final ValidationService validationService;

    public IngredientServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
        ingredientMap.put(1L, new Ingredient("картофель", 1, "кг"));
        ingredientMap.put(2L, new Ingredient("лук", 1, "кг"));
        ingredientMap.put(3L, new Ingredient("макароны", 1, "кг"));
        ingredientMap.put(4L, new Ingredient("масло", 1, "мл"));
        ingredientMap.put(5L, new Ingredient("томат", 1, "кг"));
        ingredientMap.put(6L, new Ingredient("морковь", 1, "кг"));
        ingredientMap.put(7L, new Ingredient("сельдерей", 1, "кг"));
        ingredientMap.put(8L, new Ingredient("капуста", 1, "кг"));
        ingredientMap.put(9L, new Ingredient("сыр", 1, "кг"));
        ingredientMap.put(10L, new Ingredient("манго", 1, "шт"));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (validationService.isValid(ingredient)) {
            return ingredientMap.put(idCounter++, ingredient);
        } else {
            return null;
        }
    }

    @Override
    public Ingredient editById(Long id, Ingredient ingredient) {
        if (id != null && validationService.isValid(ingredient)) {
            return ingredientMap.put(id, ingredient);
        } else {
            return null;
        }
    }

    @Override
    public Ingredient deleteById(Long id) {
        if (id != null) {
            return ingredientMap.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public Ingredient getById(Long id) {
        return ingredientMap.get(id);
    }

    @Override
    public String getAll() {
        String s = "";
        for (Long id : ingredientMap.keySet()) {
            Ingredient ingredient = ingredientMap.get(id);
            s = s + id + " " + ingredient.getName() + ", " +
                    ingredient.getQuantity() + " (" + ingredient.getMeasureUnit() + ")\n";
        }
        return s;
    }

    @Override
    public String getName(Long id) {
        return ingredientMap.get(id).getName();
    }
}
