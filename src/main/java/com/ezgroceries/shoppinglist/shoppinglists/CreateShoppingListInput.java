package com.ezgroceries.shoppinglist.shoppinglists;

public class CreateShoppingListInput {

    private String name;

    public CreateShoppingListInput() {}

    public CreateShoppingListInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
