package pro.app.recipeapp.model;

import lombok.Data;

@Data
public class Ingredient {

    private String name;
    private int quantity;
    private String measureUnit;

    public Ingredient(String name, int quantity, String measureUnit) {
        this.name = name;
        this.quantity = quantity;
        this.measureUnit = measureUnit;
    }
}
