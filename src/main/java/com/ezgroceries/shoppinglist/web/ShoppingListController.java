package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.service.CocktailService;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.ezgroceries.shoppinglist.web.dto.shoppinglists.CreateShoppingListInput;
import com.ezgroceries.shoppinglist.web.dto.shoppinglists.CreateShoppingListOutput;
import com.ezgroceries.shoppinglist.web.dto.shoppinglists.ShoppingListAddCocktailDTO;
import com.ezgroceries.shoppinglist.web.dto.shoppinglists.ShoppingListResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ShoppingListController {

    private ShoppingListService shoppingListService;

    private CocktailService cocktailService;

    @Autowired
    ShoppingListController(ShoppingListService shoppingListService, CocktailService cocktailService) {
        this.shoppingListService = shoppingListService;
        this.cocktailService = cocktailService;
    }

    @PostMapping(value = "/shopping-lists")
    public @ResponseBody ResponseEntity<CreateShoppingListOutput> createShoppingList(@RequestBody CreateShoppingListInput input) {
        ShoppingListResource shoppingListResource = shoppingListService.create(input.getName());

        if (shoppingListResource == null) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(
                new CreateShoppingListOutput(
                    shoppingListResource.getShoppingListId(),
                    shoppingListResource.getName()
                ),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = "/shopping-lists/{uuid}/cocktails")
    public @ResponseBody List<ShoppingListAddCocktailDTO> addCocktailToList(@PathVariable UUID uuid, @RequestBody List<ShoppingListAddCocktailDTO> cocktails) {
        if (cocktails.isEmpty()) {
            return Collections.emptyList();
        }
        List<UUID> cocktailIds = cocktails.stream().map(ShoppingListAddCocktailDTO::getCocktailId).collect(Collectors.toList());
        return shoppingListService.mergeCocktailsShoppingLists(cocktailIds, uuid)
                .stream()
                .map(ShoppingListAddCocktailDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/shopping-lists/{uuid}")
    public @ResponseBody ResponseEntity<ShoppingListResource> getShoppingList(@PathVariable UUID uuid) {
        Optional<ShoppingListResource> shoppingList = shoppingListService.shoppingListForId(uuid);
        shoppingList = shoppingList.map(updateList -> {
            List<UUID> cocktailIds = shoppingListService.cocktailIdsForShoppingListId(updateList.getShoppingListId());
            List<String> ingredients = cocktailService.cocktailIngredients(cocktailIds);
            updateList.setIngredients(ingredients);
            return updateList;
        });
        return ResponseEntity.of(shoppingList);
    }

    @GetMapping(value = "/shopping-lists")
    public @ResponseBody List<ShoppingListResource> getShoppingLists() {
        List<ShoppingListResource> shoppingLists = shoppingListService.allShoppingLists();
        return shoppingLists
                .stream()
                .peek(shoppingList -> {
                    List<UUID> cocktailIds = shoppingListService.cocktailIdsForShoppingListId(shoppingList.getShoppingListId());
                    List<String> ingredients = cocktailService.cocktailIngredients(cocktailIds);
                    shoppingList.setIngredients(ingredients);
                })
                .collect(Collectors.toList());
    }

}
