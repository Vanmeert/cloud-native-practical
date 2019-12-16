package com.ezgroceries.shoppinglist.web.dto.shoppinglists;

import java.util.UUID;

public class CreateShoppingListOutput {

    private UUID shoppingListId;
    private String name;

    public CreateShoppingListOutput(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }
}
