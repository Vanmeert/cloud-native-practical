package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.shoppinglists.CreateShoppingListInput;
import com.ezgroceries.shoppinglist.shoppinglists.CreateShoppingListOutput;
import com.ezgroceries.shoppinglist.shoppinglists.ShoppingListAddCocktailDTO;
import com.ezgroceries.shoppinglist.shoppinglists.ShoppingListResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping(value = "/shopping-lists/{uuid}")
    public @ResponseBody ResponseEntity<ShoppingListResource> getShoppingList(@PathVariable UUID uuid) {
        List<ShoppingListResource> shoppingLists = getDummyShoppingLists();
        Optional<ShoppingListResource> list = shoppingLists.stream()
                .filter(currentList -> uuid.equals(currentList.getShoppingListId()))
                .findAny();

        return ResponseEntity.of(list);
    }

    @GetMapping(value = "/shopping-lists")
    public @ResponseBody List<ShoppingListResource> getShoppingLists() {
        return getDummyShoppingLists();
    }

    private List<ShoppingListResource> getDummyShoppingLists() {
        return Arrays.asList(
                new ShoppingListResource(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"),
                        "Stephanie's Birthday",
                        Arrays.asList("Tequila",
                                "Triple sec",
                                "Lime juice",
                                "Salt",
                                "Blue Curacao")),
                new ShoppingListResource(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"),
                        "My Birthday",
                        Arrays.asList("Tequila",
                                "Triple sec",
                                "Lime juice",
                                "Salt",
                                "Blue Curacao"))
        );
    }

}
