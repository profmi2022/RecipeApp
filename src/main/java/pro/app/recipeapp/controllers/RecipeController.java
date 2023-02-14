package pro.app.recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.app.recipeapp.model.Recipe;
import pro.app.recipeapp.services.RecipeService;

@RestController
@RequestMapping("recipes")
public class RecipeController {

    public final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Recipe recipe) {
        try {
            Recipe r = recipeService.save(recipe);
            if (r == null) {
                throw new Exception();
            }
            return new ResponseEntity<>(r, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Recipe recipe = recipeService.getById(id);
            if (recipe == null) {
                throw new Exception();
            }
            return new ResponseEntity<>(recipe, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
