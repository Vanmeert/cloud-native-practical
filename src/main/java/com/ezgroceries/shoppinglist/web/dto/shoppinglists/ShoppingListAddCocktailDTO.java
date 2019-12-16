package com.ezgroceries.shoppinglist.web.dto.shoppinglists;

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
