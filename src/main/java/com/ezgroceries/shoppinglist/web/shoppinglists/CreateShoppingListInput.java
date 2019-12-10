package com.ezgroceries.shoppinglist.web.shoppinglists;

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
