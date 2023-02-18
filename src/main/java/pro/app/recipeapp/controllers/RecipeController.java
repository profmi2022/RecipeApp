package pro.app.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.app.recipeapp.model.Recipe;
import pro.app.recipeapp.services.RecipeService;

@Tag(
        name="Контроллер работы с рецептами",
        description="Реализация API по работе с рецептами")
@AllArgsConstructor
@RestController
@RequestMapping("recipes")
public class RecipeController {

    public final RecipeService recipeService;

    @Operation(
            summary = "Добавляет новый рецепт в коллекцию",
            description = "Добавляет рецепт в коллекцию по JSON, передаваемому в теле запроса" +
                    "если ошибка в запросе, возвращает BAD_REQUEST")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Recipe recipe) {
        Recipe r = recipeService.save(recipe);
        if (r == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(r, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Возвращает рецепт из коллекции",
            description = "Ищет рецепт по id и возвращает его значение; " +
                    "если не нашел, возвращает статус NOT_FOUND")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Recipe recipe = recipeService.getById(id);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Редактирует рецепт в коллекции по id",
            description = "Ищет рецепт по id и меняет его значение по переданному параметру; " +
                    "если не нашел, возвращает статус NOT_FOUND")
    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editById(id, recipe);
        if (recipe1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe1, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Удаляет рецепт из коллекции по id",
            description = "Удаляет рецепт по id; " +
                    "если не нашел, возвращает статус NOT_FOUND")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Recipe recipe = recipeService.deleteById(id);
        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Возвращает всю коллекцию рецептов",
            description = "Возвращает все рецепты и статус OK")
    @GetMapping
    public ResponseEntity<?> getAll() {

        return new ResponseEntity<>(recipeService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Возвращает всю коллекцию рецептов с разбивкой на страницы",
            description = "Передаются два параметра: номер страницы и количество рецептов на страницу")
    @GetMapping("/paged")
    public ResponseEntity<?> getAll(@RequestParam int page, @RequestParam int recipePerPage) {

        return new ResponseEntity<>(recipeService.getAll(page, recipePerPage), HttpStatus.OK);
    }

    @Operation(
            summary = "Возвращает коллекцию рецептов, содержащих набор ингредиентов, переданных в качестве параметров",
            description = "Ингредиенты передаются по id, может быть задано любое количество ингредиентов")
    @GetMapping("/contains")
    public ResponseEntity<?> getByIngredient(@RequestParam Long... ids){

        return new ResponseEntity<>(recipeService.getOutString(recipeService.getByIngredient(ids)), HttpStatus.OK);
    }

    @Operation(
            summary = "Возвращает коллекцию рецептов, содержащих набор ингредиентов, переданных в качестве параметров," +
                    "с разбивкой на страницы",
            description = "Передаются: номер страницы и количество рецептов на страницу, " +
                    "ингредиенты передаются по id, может быть задано любое количество ингредиентов. ")
    @GetMapping("/contains_paged")
    public ResponseEntity<?> getByIngredient(@RequestParam int page, @RequestParam int recipePerPage, @RequestParam Long... ids){

        return new ResponseEntity<>(recipeService.getOutString(page, recipePerPage, recipeService.getByIngredient(ids)),
                HttpStatus.OK);
    }
}
