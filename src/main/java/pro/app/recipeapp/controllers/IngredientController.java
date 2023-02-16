package pro.app.recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.services.IngredientService;

import java.util.Map;

@RestController
@RequestMapping("ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Ingredient ingredient) {
        Ingredient i = ingredientService.save(ingredient);
        if (i == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(i, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getById(id);
        if (ingredient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable Long id, Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editById(id, ingredient);
        if (ingredient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ingredient1, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.deleteById(id);
        if (ingredient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(ingredientService.getAll(), HttpStatus.OK);
    }
}

