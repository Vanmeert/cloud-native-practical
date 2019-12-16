package com.ezgroceries.shoppinglist.service.external;

import com.ezgroceries.shoppinglist.db.CocktailRepository;
import com.ezgroceries.shoppinglist.db.entities.CocktailEntity;
import org.springframework.stereotype.Component;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1", fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);

    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }

        @Override
        public CocktailDBResponse searchCocktails(String search) {
            List<CocktailEntity> cocktailEntities = cocktailRepository.findByNameContainingIgnoreCase(search);

            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktailEntity -> {
                CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
                drinkResource.setIdDrink(cocktailEntity.getIdDrink());
                drinkResource.setStrDrink(cocktailEntity.getName());
                drinkResource.setStrGlass(cocktailEntity.getGlass());
                drinkResource.setStrInstructions(cocktailEntity.getInstructions());
                drinkResource.setStrDrinkThumb(cocktailEntity.getImage());

                List<String> ingredients = new ArrayList<>(cocktailEntity.getIngredients());
                drinkResource.setStrIngredient1(ingredientFromList(ingredients, 0));
                drinkResource.setStrIngredient2(ingredientFromList(ingredients, 1));
                drinkResource.setStrIngredient3(ingredientFromList(ingredients, 2));
                drinkResource.setStrIngredient4(ingredientFromList(ingredients, 3));
                drinkResource.setStrIngredient5(ingredientFromList(ingredients, 4));
                drinkResource.setStrIngredient6(ingredientFromList(ingredients, 5));
                drinkResource.setStrIngredient7(ingredientFromList(ingredients, 6));
                drinkResource.setStrIngredient8(ingredientFromList(ingredients, 7));
                drinkResource.setStrIngredient9(ingredientFromList(ingredients, 8));
                drinkResource.setStrIngredient10(ingredientFromList(ingredients, 9));
                drinkResource.setStrIngredient11(ingredientFromList(ingredients, 10));
                drinkResource.setStrIngredient12(ingredientFromList(ingredients, 11));
                drinkResource.setStrIngredient13(ingredientFromList(ingredients, 12));
                drinkResource.setStrIngredient14(ingredientFromList(ingredients, 13));
                drinkResource.setStrIngredient15(ingredientFromList(ingredients, 14));

                return drinkResource;
            }).collect(Collectors.toList()));

            return cocktailDBResponse;
        }

        private static String ingredientFromList(List<String> ingredients, int index) {
            return ingredients.size() > index ? ingredients.get(index) : null;
        }
    }

}
