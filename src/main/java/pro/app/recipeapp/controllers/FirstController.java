package pro.app.recipeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping
    public String AppStart() {
        return "Приложение запущено";
    }
    @GetMapping("/info")
    public String AppInfo() {
        return "<ul><li>Мария Исакова</li><li>RecipeApp</li><li>03.02.2023</li></ul>Моя первая гениальная программа, которую я <b><i>разрабатывала весь вечер</i></b> :D";
    }
    @GetMapping("/fun")
    public String AppFun(){
        return "<b><i>It's perfect</i></b><br><br>" +
                "/)/)(\\(\\ <br>" +
                "(':')(':') <br>" +
                "((\")(\")) <br>" ;
    }

}