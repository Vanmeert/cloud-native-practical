package com.ezgroceries.shoppinglist.db;

import com.ezgroceries.shoppinglist.db.entities.CocktailEntity;

import java.util.List;
import java.util.UUID;

public interface CocktailRepository extends BaseRepository<CocktailEntity, UUID> {

    CocktailEntity findByIdDrink(String idDrink);

    CocktailEntity findByName(String name);

    List<CocktailEntity> findByNameContainingIgnoreCase(String name);

    List<CocktailEntity> findByIdDrinkIn(List<String> idDrinks);

    List<CocktailEntity> findByIdIn(List<UUID> ids);

}
