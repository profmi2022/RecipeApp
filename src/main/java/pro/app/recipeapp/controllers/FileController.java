package pro.app.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pro.app.recipeapp.services.IngredientService;
import pro.app.recipeapp.services.RecipeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@Data
@RestController
@AllArgsConstructor
@Tag(name = "Контроллер для работы с файлами", description = "Загрузка / выгрузка файлов рецептов и ингредиентов")
public class FileController {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    @PostMapping(value = "/recipes/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка на сервер файла рецептов", description = "Формат файла JSON")
    public ResponseEntity<String> uploadRecipesFile(@RequestParam MultipartFile file) throws Exception {
        try {

            recipeService.uploadFile(file);
            return ResponseEntity.ok().build();

        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/ingredients/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка на сервер файла ингредиентов", description = "Формат файла JSON")
    public ResponseEntity<String> uploadIngredientsFile(@RequestParam MultipartFile file) throws Exception {
        try {

            ingredientService.uploadFile(file);
            return ResponseEntity.ok().build();

        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/recipes/download")
    @Operation(summary = "Выгрузка файла рецептов")
    public ResponseEntity<InputStreamResource> downloadRecipesFile() {
        try {
            File recipeFile = recipeService.getRecipeFileName();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(recipeFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(recipeFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + recipeFile.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/ingredients/download")
    @Operation(summary = "Выгрузка файла ингредиентов")
    public ResponseEntity<InputStreamResource> downloadIngredientsFile() {
        try {
            File ingredientFile = ingredientService.getIngredientFileName();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(ingredientFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(ingredientFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ingredientFile.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }
}
