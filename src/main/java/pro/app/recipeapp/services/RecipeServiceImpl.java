package pro.app.recipeapp.services;

import org.springframework.stereotype.Service;
import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService{

    private static final Map<Long, Recipe> recipeMap = new TreeMap<>();
    private static Long idCounter = 1L;
    private final ValidationService validationService;
    private final IngredientService ingredientService;

    public RecipeServiceImpl(ValidationService validationService, IngredientService ingredientService) {

        this.validationService = validationService;
        this.ingredientService = ingredientService;
        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("картофель", 1, "кг"));
        ingredients1.add(new Ingredient("лук", 1, "кг"));
        ingredients1.add(new Ingredient("томат", 1, "кг"));
        ingredients1.add(new Ingredient("морковь", 1, "кг"));

        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("сельдерей", 1, "кг"));
        ingredients2.add(new Ingredient("лук", 1, "кг"));
        ingredients2.add(new Ingredient("томат", 1, "кг"));
        ingredients2.add(new Ingredient("петрушка", 1, "кг"));
        ingredients2.add(new Ingredient("масло", 100, "мл"));

        List<Ingredient> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredient("капуста", 1, "кг"));
        ingredients3.add(new Ingredient("сыр", 1, "кг"));
        ingredients3.add(new Ingredient("масло", 50, "мл"));
        ingredients3.add(new Ingredient("морковь", 1, "кг"));

        List<Ingredient> ingredients4 = new ArrayList<>();
        ingredients4.add(new Ingredient("ананас", 1, "шт"));
        ingredients4.add(new Ingredient("апельсин", 1, "кг"));
        ingredients4.add(new Ingredient("манго", 1, "шт"));
        ingredients4.add(new Ingredient("масло", 1, "мл"));

        List<Ingredient> ingredients5 = new ArrayList<>();
        ingredients5.add(new Ingredient("масло", 1, "кг"));
        ingredients5.add(new Ingredient("лук", 1, "кг"));
        ingredients5.add(new Ingredient("картофель", 1, "кг"));
        ingredients5.add(new Ingredient("томат", 1, "кг"));
        ingredients5.add(new Ingredient("сельдерей", 1, "кг"));

        List<String> steps = new ArrayList<>();
        steps.add("1. Бульон приготовить");
        steps.add("2. Овощи почистить и нарезать");
        steps.add("3. Все остальное...");

        recipeMap.put(1L, new Recipe("Суп", 40, ingredients1, steps));
        recipeMap.put(2L, new Recipe("Суп", 40, ingredients1, steps));
        recipeMap.put(3L, new Recipe("Суп-пюре", 30, ingredients2, steps));
        recipeMap.put(4L, new Recipe("Тарталетка", 30, ingredients2, steps));
        recipeMap.put(5L, new Recipe("Антрекот", 30, ingredients3, steps));
        recipeMap.put(6L, new Recipe("Салат", 30, ingredients4, steps));
        recipeMap.put(7L, new Recipe("Тортик", 50, ingredients5, steps));
        recipeMap.put(8L, new Recipe("Пирог", 30, ingredients2, steps));
        recipeMap.put(9L, new Recipe("Драники", 40, ingredients1, steps));
        recipeMap.put(10L, new Recipe("Кисель", 30, ingredients3, steps));
        recipeMap.put(11L, new Recipe("Запеканка", 30, ingredients4, steps));
        recipeMap.put(12L, new Recipe("Лазанья", 70, ingredients1, steps));
        recipeMap.put(13L, new Recipe("Сырники", 30, ingredients5, steps));
        recipeMap.put(14L, new Recipe("Гуляш", 50, ingredients3, steps));
        recipeMap.put(15L, new Recipe("Котлеты", 30, ingredients2, steps));
        recipeMap.put(16L, new Recipe("Наполеон", 35, ingredients4, steps));
    }

    @Override
    public Recipe save(Recipe recipe) {
        if (validationService.isValid(recipe)) {
            return recipeMap.put(idCounter++, recipe);
        } else {
            return null;
        }
    }

    @Override
    public Recipe editById(Long id, Recipe recipe) {
        if (id != null && validationService.isValid(recipe)) {
            return recipeMap.put(id, recipe);
        } else {
            return null;
        }
    }

    @Override
    public Recipe deleteById(Long id) {
        if (id != null) {
            return recipeMap.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public Recipe getById(Long id) {
        return recipeMap.get(id);
    }

    @Override
    public String getAll() {
        return getOutString(recipeMap);
    }

    @Override
    public String getAll(int page, int recipePerPage) {
        return getOutString(page, recipePerPage, recipeMap);
    }

    // Подготовка списка рецепта для вывода
    @Override
    public String getOutString(Map<Long, Recipe> recipeMap) {
        String s = "";
        for (Long id : recipeMap.keySet()) {
            Recipe recipe  = recipeMap.get(id);
            if(s != ""){
                s = s + "\n";
            }
            s = s + id + " " + recipe.getTitle() + ", " +
                    recipe.getCookingTime();
        }
        return s;
    }

    @Override
    public String getOutString(int page, int recipePerPage, Map<Long, Recipe> recipeMap) {
        Map<Long, Recipe> outMap = new TreeMap<>();
        {
            int i = 1;
            for (Long aLong : recipeMap.keySet()) {
                if (i > (page - 1) * recipePerPage && i <= page * recipePerPage) {
                    outMap.put(aLong, recipeMap.get(aLong));
                }
                i++;
            }
        }
        return getOutString(outMap);
    }

    @Override
    public Map<Long, Recipe> getByIngredient(Long ...ids) {
        ArrayList<String> searchIngredientNames = new ArrayList(ids.length);
        for (Long id : ids) {
            searchIngredientNames.add(ingredientService.getName(id));   //входные параметры ids преобразовала в строки
        }

        Map<Long, Recipe> foundRecipesMap = new TreeMap<>();
        for (Long aLong : recipeMap.keySet()) {                     //проверка коллекции рецептов
            Recipe recipe = recipeMap.get(aLong);
            ArrayList<String> recipeIngredientNames = new ArrayList<>();
            for (Ingredient ingredient : recipe.getIngredients()) { //создание списка имен ингредиентов из рецепта
                recipeIngredientNames.add(ingredient.getName());
            }
            if(recipeIngredientNames.containsAll(searchIngredientNames)){
                foundRecipesMap.put(aLong, recipe);//если все искомые ингредиенты входят в список ингредиентов
                // из рецепта, помещаем рецепт в итоговую коллекцию
            }
        }
        return foundRecipesMap;
    }
}
