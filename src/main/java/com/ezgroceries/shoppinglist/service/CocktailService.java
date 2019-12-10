package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.db.CocktailEntity;
import com.ezgroceries.shoppinglist.db.CocktailRepository;
import com.ezgroceries.shoppinglist.web.cocktails.CocktailResource;
import com.ezgroceries.shoppinglist.web.cocktails.external.CocktailDBClient;
import com.ezgroceries.shoppinglist.web.cocktails.external.CocktailDBResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CocktailService {

    private final CocktailDBClient cocktailDBClient;

    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailDBClient cocktailDBClient, CocktailRepository cocktailRepository) {
        this.cocktailDBClient = cocktailDBClient;
        this.cocktailRepository = cocktailRepository;
    }

    public List<CocktailResource> cocktails(String search) {
        List<CocktailDBResponse.DrinkResource> drinks = cocktailDBClient.searchCocktails(search).getDrinks();
        return mergeCocktails(drinks);
    }

    private List<CocktailResource> mergeCocktails(List<CocktailDBResponse.DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                CocktailEntity newCocktailEntity = new CocktailEntity();
                newCocktailEntity.setId(UUID.randomUUID());
                newCocktailEntity.setIdDrink(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                newCocktailEntity.setIngredients(new HashSet<>(getIngredients(drinkResource)));
                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    public List<String> cocktailIngredients(List<UUID> cocktailIds) {
        List<String> ingredients = new ArrayList<>();
        cocktailRepository.findByIdIn(cocktailIds)
                .forEach(entity -> ingredients.addAll(entity.getIngredients()));
        return ingredients
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private List<CocktailResource> mergeAndTransform(List<CocktailDBResponse.DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailResource(
                allEntityMap.get(drinkResource.getIdDrink()).getId(),
                drinkResource.getStrDrink(),
                drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(),
                drinkResource.getStrDrinkThumb(),
                getIngredients(drinkResource))
        ).collect(Collectors.toList());
    }

    private List<String> getIngredients(CocktailDBResponse.DrinkResource drink) {
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
        return ingredients;
    }

    private void addIngredientToList(String ingredient, List<String> list) {
        if (ingredient != null && !ingredient.isEmpty()) {
            list.add(ingredient);
        }
    }

}
