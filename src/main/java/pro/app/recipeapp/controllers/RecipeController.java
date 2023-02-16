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
        Recipe r = recipeService.save(recipe);
        if (r == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(r, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Recipe recipe = recipeService.getById(id);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable Long id, Recipe recipe) {
        Recipe recipe1 = recipeService.editById(id, recipe);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe1, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Recipe recipe = recipeService.deleteById(id);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(recipeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAll(@RequestParam int page, int recipePerPage) {

        return new ResponseEntity<>(recipeService.getAll(page, recipePerPage), HttpStatus.OK);
    }

    @GetMapping("/contains")
    public ResponseEntity<?> getByIngredient(@RequestParam Long... ids){
        return new ResponseEntity<>(recipeService.getOutString(recipeService.getByIngredient(ids)), HttpStatus.OK);
    }

    @GetMapping("/contains_paged")
    public ResponseEntity<?> getByIngredient(@RequestParam int page, int recipePerPage, Long... ids){

        return new ResponseEntity<>(recipeService.getOutString(page, recipePerPage, recipeService.getByIngredient(ids)),
                HttpStatus.OK);
    }
}
