package pro.app.recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.services.IngredientService;

@RestController
@RequestMapping("ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Ingredient ingredient) {
        try {
            Ingredient i = ingredientService.save(ingredient);
            if (i == null) {
                throw new Exception();
            }
            return new ResponseEntity<>(i, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) {
                throw new Exception();
            }
            return new ResponseEntity<>(ingredient, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
