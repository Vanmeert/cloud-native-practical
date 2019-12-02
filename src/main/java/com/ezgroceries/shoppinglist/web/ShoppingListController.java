package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.shoppinglists.CreateShoppingListInput;
import com.ezgroceries.shoppinglist.shoppinglists.CreateShoppingListOutput;
import com.ezgroceries.shoppinglist.shoppinglists.ShoppingListAddCocktailDTO;
import com.ezgroceries.shoppinglist.shoppinglists.ShoppingListResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class ShoppingListController {

    @PostMapping(value = "/shopping-lists")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody CreateShoppingListOutput createShoppingList(@RequestBody CreateShoppingListInput input) {
        return new CreateShoppingListOutput(UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"), input.getName());
    }

    @PostMapping(value = "/shopping-lists/{uuid}/cocktails")
    public @ResponseBody List<ShoppingListAddCocktailDTO> addCocktailToList(@PathVariable UUID uuid, @RequestBody List<ShoppingListAddCocktailDTO> cocktails) {
        if (cocktails.isEmpty()) {
            return Collections.emptyList();
        }
        return cocktails.subList(0, 1);
    }

}
