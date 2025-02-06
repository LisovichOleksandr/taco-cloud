package ua.lisovich.tacos.entity;

import lombok.Data;

@Data
public class Ingredient {
    private final String id;
    private final String name;
    private final TypeIngredient type;

}
