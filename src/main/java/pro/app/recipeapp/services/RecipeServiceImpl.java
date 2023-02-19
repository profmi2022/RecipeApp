package pro.app.recipeapp.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.model.Recipe;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private Map<Long, Recipe> recipeMap = new TreeMap<>();
    private static Long idCounter = 1L;
    private final ValidationService validationService;
    private final IngredientService ingredientService;
    private final FileService fileService;
    @Value("${path.to.file.recipes}")
    private String recipesPath;
    @Value("${name.of.file.recipes}")
    private String recipesFile;
    @Value("${name.of.file.recipes.text}")
    private String recipesFileText;

    public RecipeServiceImpl(ValidationService validationService, IngredientService ingredientService, FileService fileService) {

        this.validationService = validationService;
        this.ingredientService = ingredientService;
        this.fileService = fileService;
//        List<Ingredient> ingredients1 = new ArrayList<>();
//
//        ingredients1.add(new Ingredient("картофель", 1, "кг"));
//        ingredients1.add(new Ingredient("лук", 1, "кг"));
//        ingredients1.add(new Ingredient("томат", 1, "кг"));
//        ingredients1.add(new Ingredient("морковь", 1, "кг"));
//
//        List<Ingredient> ingredients2 = new ArrayList<>();
//        ingredients2.add(new Ingredient("сельдерей", 1, "кг"));
//        ingredients2.add(new Ingredient("лук", 1, "кг"));
//        ingredients2.add(new Ingredient("томат", 1, "кг"));
//        ingredients2.add(new Ingredient("петрушка", 1, "кг"));
//        ingredients2.add(new Ingredient("масло", 100, "мл"));
//
//        List<Ingredient> ingredients3 = new ArrayList<>();
//        ingredients3.add(new Ingredient("капуста", 1, "кг"));
//        ingredients3.add(new Ingredient("сыр", 1, "кг"));
//        ingredients3.add(new Ingredient("масло", 50, "мл"));
//        ingredients3.add(new Ingredient("морковь", 1, "кг"));
//
//        List<Ingredient> ingredients4 = new ArrayList<>();
//        ingredients4.add(new Ingredient("ананас", 1, "шт"));
//        ingredients4.add(new Ingredient("апельсин", 1, "кг"));
//        ingredients4.add(new Ingredient("манго", 1, "шт"));
//        ingredients4.add(new Ingredient("масло", 1, "мл"));
//
//        List<Ingredient> ingredients5 = new ArrayList<>();
//        ingredients5.add(new Ingredient("масло", 1, "кг"));
//        ingredients5.add(new Ingredient("лук", 1, "кг"));
//        ingredients5.add(new Ingredient("картофель", 1, "кг"));
//        ingredients5.add(new Ingredient("томат", 1, "кг"));
//        ingredients5.add(new Ingredient("сельдерей", 1, "кг"));
//
//        List<String> steps = new ArrayList<>();
//        steps.add("1. Бульон приготовить");
//        steps.add("2. Овощи почистить и нарезать");
//        steps.add("3. Все остальное...");
//
//        recipeMap.put(1L, new Recipe("Суп", 40, ingredients1, steps));
//        recipeMap.put(2L, new Recipe("Бульон", 40, ingredients1, steps));
//        recipeMap.put(3L, new Recipe("Суп-пюре", 30, ingredients2, steps));
//        recipeMap.put(4L, new Recipe("Тарталетка", 30, ingredients2, steps));
//        recipeMap.put(5L, new Recipe("Антрекот", 30, ingredients3, steps));
//        recipeMap.put(6L, new Recipe("Салат", 30, ingredients4, steps));
//        recipeMap.put(7L, new Recipe("Тортик", 50, ingredients5, steps));
//        recipeMap.put(8L, new Recipe("Пирог", 30, ingredients2, steps));
//        recipeMap.put(9L, new Recipe("Драники", 40, ingredients1, steps));
//        recipeMap.put(10L, new Recipe("Кисель", 30, ingredients3, steps));
//        recipeMap.put(11L, new Recipe("Запеканка", 30, ingredients4, steps));
//        recipeMap.put(12L, new Recipe("Лазанья", 70, ingredients1, steps));
//        recipeMap.put(13L, new Recipe("Сырники", 30, ingredients5, steps));
//        recipeMap.put(14L, new Recipe("Гуляш", 50, ingredients3, steps));
//        recipeMap.put(15L, new Recipe("Котлеты", 30, ingredients2, steps));
//        recipeMap.put(16L, new Recipe("Наполеон", 35, ingredients4, steps));
    }

    @Override
    public Recipe save(Recipe recipe) {
        if (validationService.isValid(recipe)) {
            recipeMap.put(idCounter++, recipe);
            fileService.saveToFile(recipeMap, recipesPath, recipesFile);
            return recipe;
        } else {
            return null;
        }
    }

    @Override
    public Recipe editById(Long id, Recipe recipe) {
        if (id != null && recipeMap.containsKey(id) && validationService.isValid(recipe)) {
            recipeMap.put(id, recipe);
            fileService.saveToFile(recipeMap, recipesPath, recipesFile);
            return recipe;
        } else {
            return null;
        }
    }

    @Override
    public Recipe deleteById(Long id) {
        if (id != null) {
            Recipe recipe = recipeMap.remove(id);
            fileService.saveToFile(recipeMap, recipesPath, recipesFile);
            return recipe;
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
            Recipe recipe = recipeMap.get(id);
            if (!s.isEmpty()) {
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
    public Map<Long, Recipe> getByIngredient(Long... ids) {
        List<String> searchIngredientNames = new ArrayList<>(ids.length);
        for (Long id : ids) {
            searchIngredientNames.add(ingredientService.getName(id));   //входные параметры ids преобразовала в строки
        }

        Map<Long, Recipe> foundRecipesMap = new TreeMap<>();
        for (Long aLong : recipeMap.keySet()) {                     //проверка коллекции рецептов
            Recipe recipe = recipeMap.get(aLong);
            List<String> recipeIngredientNames = new ArrayList<>();
            for (Ingredient ingredient : recipe.getIngredients()) { //создание списка имен ингредиентов из рецепта
                recipeIngredientNames.add(ingredient.getName());
            }
            if (recipeIngredientNames.containsAll(searchIngredientNames)) {
                foundRecipesMap.put(aLong, recipe);//если все искомые ингредиенты входят в список ингредиентов
                // из рецепта, помещаем рецепт в итоговую коллекцию
            }
        }
        return foundRecipesMap;
    }

    @PostConstruct
    private void init() {

        recipeMap = fileService.readFromFile(recipesPath, recipesFile, new TypeReference<>() {
        });
        if (recipeMap == null) {
            idCounter = 1L;
            recipeMap = new TreeMap<>();
        } else {
            idCounter = (long) (recipeMap.size() + 1);
        }
    }

    public void uploadFile(MultipartFile file) throws Exception {

        fileService.uploadFile(file, recipesPath);
        Map<Long, Recipe> newRecipeMap = fileService.readFromFile(recipesPath, file.getOriginalFilename(), new TypeReference<>() {
        });
        if (newRecipeMap != null) {
            recipeMap = newRecipeMap;
            idCounter = (long) (recipeMap.size() + 1);
            fileService.saveToFile(recipeMap, recipesPath, recipesFile);
        }
    }

    public File getRecipeFileName() {
        return new File(recipesPath, recipesFile);
    }

    public File getRecipeFileNameText() {
        return new File(recipesPath, recipesFileText);
    }

    public void prepareRecipesText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Список рецептов:");
        for (Long aLong : recipeMap.keySet()) {
            Recipe recipe = recipeMap.get(aLong);
            sb.append("\n\n").append(recipe.getTitle());
            sb.append("\nВремя приготовления: ").append(recipe.getCookingTime());
            sb.append("\nИнгредиенты:");
            for (Ingredient ingredient : recipe.getIngredients()) {
                sb.append("\n • ").append(ingredient.getName()).append(" - ").
                        append(ingredient.getQuantity()).append(" ").
                        append(ingredient.getMeasureUnit());
            }
            for (String step : recipe.getSteps()) {
                sb.append("\n").append(step);
            }
        }
        try {
            Path path = Path.of(recipesPath, recipesFileText);
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
