package pro.app.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;
import pro.app.recipeapp.model.Ingredient;

import java.io.File;


public interface IngredientService {

    Ingredient save(Ingredient ingredient);
    Ingredient getById(Long id);
    Ingredient editById(Long id, Ingredient ingredient);
    Ingredient deleteById(Long id);
    String getAll();
    String getName(Long id);
    void uploadFile(MultipartFile file) throws Exception;
    File getIngredientFileName();


}
