package com.ezgroceries.shoppinglist.cocktails;

import java.util.List;
import java.util.UUID;

public class CocktailResource {

    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;

    public CocktailResource(
            UUID cocktailId,
            String name,
            String glass,
            String instructions,
            String image,
            List<String> ingredients
    ) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
    }

}
