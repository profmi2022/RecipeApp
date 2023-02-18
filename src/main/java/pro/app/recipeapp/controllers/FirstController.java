package pro.app.recipeapp.controllers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @Info(
                title = "Приложение рецептов",
                description = "Приложение для создания и выбора рецептов по ингредиентам", version = "1.0.0",
                contact = @Contact(
                        name = "Исакова Мария")
        )
)
@RestController
@Tag(
        name="Стартовый контроллер",
        description="Показывает. что приложение запущено и выводит информацию о приложении")
public class FirstController {

    @Operation(
            summary = "Индикация запуска приложение",
            description = "Сообщение о запуске приложения")
    @GetMapping
    public String appStart() {
        return "Приложение запущено";
    }

    @Operation(
            summary = "Информация о приложении",
            description = "Выводит сообщение с информацией о приложении")
    @GetMapping("/info")
    public String appInfo() {
        return "<ul><li>Мария Исакова</li><li>RecipeApp</li><li>03.02.2023</li></ul>Моя первая гениальная программа, которую я <b><i>разрабатывала весь вечер</i></b> :D";
    }

    @Operation(
            summary = "Индикация настроения",
            description = "Вывод зайчиков")
    @GetMapping("/fun")
    public String appFun(){
        return "<b><i>It's perfect</i></b><br><br>" +
                "/)/)(\\(\\ <br>" +
                "(':')(':') <br>" +
                "((\")(\")) <br>" ;
    }

}