package pro.app.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;
import pro.app.recipeapp.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface RecipeService {


    Recipe save(Recipe recipe);
    Recipe getById(Long id);
    Recipe editById(Long id, Recipe recipe);
    Recipe deleteById(Long id);
    String getAll();
    String getAll(int page, int recipePerPage);
    String getOutString(Map<Long, Recipe> recipeMap);
    String getOutString(int page, int recipePerPage,Map<Long, Recipe> recipeMap);
    Map<Long, Recipe> getByIngredient(Long ... ids);
    void uploadFile(MultipartFile file) throws Exception;
    File getRecipeFileName();
    File getRecipeFileNameText();
    void prepareRecipesText();

    }
