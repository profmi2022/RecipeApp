package pro.app.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.app.recipeapp.model.Ingredient;
import pro.app.recipeapp.services.IngredientService;

@Tag(
        name="Контроллер работы с ингредиентами",
        description="Реализация API по работе с ингредиентами")
@AllArgsConstructor
@RestController
@RequestMapping("ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Operation(
            summary = "Добавляет новый ингредиент в коллекцию",
            description = "Добавляет ингредиент в коллекцию по JSON, передаваемому в теле запроса")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Ингредиент успешно добавлен",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Ошибка в запросе, ингредиент не добавлен",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
            }
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Ingredient ingredient) {
        Ingredient i = ingredientService.save(ingredient);
        if (i == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(i, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Возвращает ингредиент из коллекции",
            description = "Ищет ингредиент по id и возвращает его значение")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Ингредиент по id успешно найден",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Ингредиент по id не найден",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getById(id);
        if (ingredient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Редактирует ингредиент в коллекции по id",
            description = "Ищет ингредиент по id и меняет его значение по переданному параметру")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Ингредиент по id успешно отредактирован",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Ингредиент по id не найден",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> editById(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editById(id, ingredient);
        if (ingredient1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ingredient1, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Удаляет ингредиент из коллекции по id",
            description = "Удаляет ингредиент по id; " +
                    "если не нашел, возвращает статус NOT_FOUND")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Ингредиент по id успешно удален",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Ингредиент по id не найден",
                            content = {
                                    @Content(schema = @Schema(implementation = Ingredient.class))}),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.deleteById(id);
        if (ingredient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Возвращает всю коллекцию ингредиентов",
            description = "Возвращает все ингредиенты и статус OK")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(ingredientService.getAll(), HttpStatus.OK);
    }
}

