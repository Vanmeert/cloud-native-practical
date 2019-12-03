package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.cocktails.CocktailResource;
import com.ezgroceries.shoppinglist.cocktails.external.CocktailDBClient;
import com.ezgroceries.shoppinglist.cocktails.external.CocktailDBResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CocktailController {

    @Autowired
    private CocktailDBClient cocktailDBClient;

    @GetMapping(value = "/cocktails")
    public @ResponseBody List<CocktailResource> get(@RequestParam String search) {
        return mapDrinksFromCocktailDB(search);
    }

    private List<CocktailResource> mapDrinksFromCocktailDB(String search) {
        List<CocktailDBResponse.DrinkResource> drinks = cocktailDBClient.searchCocktails(search).getDrinks();
        return drinks.stream()
                .map(this::cocktailFromDrink)
                .collect(Collectors.toList());
    }

    private CocktailResource cocktailFromDrink(CocktailDBResponse.DrinkResource drink) {
        List<String> ingredients = new ArrayList<>();
        addIngredientToList(drink.getStrIngredient1(), ingredients);
        addIngredientToList(drink.getStrIngredient2(), ingredients);
        addIngredientToList(drink.getStrIngredient3(), ingredients);
        addIngredientToList(drink.getStrIngredient4(), ingredients);
        addIngredientToList(drink.getStrIngredient5(), ingredients);
        addIngredientToList(drink.getStrIngredient6(), ingredients);
        addIngredientToList(drink.getStrIngredient7(), ingredients);
        addIngredientToList(drink.getStrIngredient8(), ingredients);
        addIngredientToList(drink.getStrIngredient9(), ingredients);
        addIngredientToList(drink.getStrIngredient10(), ingredients);
        addIngredientToList(drink.getStrIngredient11(), ingredients);
        addIngredientToList(drink.getStrIngredient12(), ingredients);
        addIngredientToList(drink.getStrIngredient13(), ingredients);
        addIngredientToList(drink.getStrIngredient14(), ingredients);
        addIngredientToList(drink.getStrIngredient15(), ingredients);

        return new CocktailResource(
                UUID.nameUUIDFromBytes(drink.getIdDrink().getBytes()),
                drink.getStrDrink(),
                drink.getStrGlass(),
                drink.getStrInstructions(),
                drink.getStrDrinkThumb(),
                ingredients
        );
    }

    private void addIngredientToList(String ingredient, List<String> list) {
        if (ingredient != null && !ingredient.isEmpty()) {
            list.add(ingredient);
        }
    }
}
