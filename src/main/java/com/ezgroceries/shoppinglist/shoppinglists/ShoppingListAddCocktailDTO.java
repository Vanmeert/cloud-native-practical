package com.ezgroceries.shoppinglist.shoppinglists;

import java.util.UUID;

public class ShoppingListAddCocktailDTO {

    private UUID cocktailId;

    public ShoppingListAddCocktailDTO() {}

    public ShoppingListAddCocktailDTO(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }
}
