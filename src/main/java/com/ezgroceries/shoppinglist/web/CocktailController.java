package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.cocktails.CocktailResource;
import com.ezgroceries.shoppinglist.cocktails.external.CocktailDBClient;
import com.ezgroceries.shoppinglist.cocktails.external.CocktailDBResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    private List<CocktailResource> getDummyResources() {
        return Arrays.asList(
                new CocktailResource(
                        UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"),
                        "Margerita",
                        "Cocktail glass",
                        "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                        "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")),
                new CocktailResource(
                        UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"),
                        "Blue Margerita",
                        "Cocktail glass",
                        "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                        "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                        Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt")));
    }
}
