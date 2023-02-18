package pro.app.recipeapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.app.recipeapp.model.Ingredient;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {

    private Map<Long, Ingredient> ingredientMap = new TreeMap<>();
    private static Long idCounter = 1L;
    private final ValidationService validationService;
    private final FileService fileService;
    @Value("${path.to.file.ingredients}")
    private String ingredientsPath;
    @Value("${name.of.file.ingredients}")
    private String ingredientsFile;

    private final ObjectMapper objectMapper2;

    public IngredientServiceImpl(ValidationService validationService, FileService fileService, ObjectMapper objectMapper2) {
        this.validationService = validationService;
        this.fileService = fileService;
        this.objectMapper2 = objectMapper2;
//        ingredientMap.put(1L, new Ingredient("картофель", 1, "кг"));
//        ingredientMap.put(2L, new Ingredient("лук", 1, "кг"));
//        ingredientMap.put(3L, new Ingredient("макароны", 1, "кг"));
//        ingredientMap.put(4L, new Ingredient("масло", 1, "мл"));
//        ingredientMap.put(5L, new Ingredient("томат", 1, "кг"));
//        ingredientMap.put(6L, new Ingredient("морковь", 1, "кг"));
//        ingredientMap.put(7L, new Ingredient("сельдерей", 1, "кг"));
//        ingredientMap.put(8L, new Ingredient("капуста", 1, "кг"));
//        ingredientMap.put(9L, new Ingredient("сыр", 1, "кг"));
//        ingredientMap.put(10L, new Ingredient("манго", 1, "шт"));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (validationService.isValid(ingredient)) {
            ingredientMap.put(idCounter++, ingredient);
            fileService.saveToFile(ingredientMap, ingredientsPath, ingredientsFile);
            return ingredient;
        } else {
            return null;
        }
    }

    @Override
    public Ingredient editById(Long id, Ingredient ingredient) {
        if (id != null && ingredientMap.containsKey(id) && validationService.isValid(ingredient)) {
            ingredientMap.put(id, ingredient);
            fileService.saveToFile(ingredientMap, ingredientsPath, ingredientsFile);
            return ingredient;
        } else {
            return null;
        }
    }

    @Override
    public Ingredient deleteById(Long id) {
        if (id != null) {
            Ingredient ingredient = ingredientMap.remove(id);
            fileService.saveToFile(ingredientMap, ingredientsPath, ingredientsFile);
            return ingredient;
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
        try {
            return objectMapper2.writeValueAsString(ingredientMap);
        } catch (Exception e) {
            return null;
        }
//        String s = "";
//        for (Long id : ingredientMap.keySet()) {
//            Ingredient ingredient = ingredientMap.get(id);
//            s = s + id + " " + ingredient.getName() + ", " +
//                    ingredient.getQuantity() + " (" + ingredient.getMeasureUnit() + ")\n";
//        }
//        return s;
    }

    @Override
    public String getName(Long id) {

        if (ingredientMap.containsKey(id)) {
            return ingredientMap.get(id).getName();
        } else {
            return null;
        }
    }

    @PostConstruct
    private void init() {

        ingredientMap = fileService.readFromFile(ingredientsPath, ingredientsFile, new TypeReference<>() { });
        if (ingredientMap == null) {
            idCounter = 1L;
            ingredientMap = new TreeMap<>();
        } else {
            idCounter = (long) (ingredientMap.size() + 1);
        }
    }
}
