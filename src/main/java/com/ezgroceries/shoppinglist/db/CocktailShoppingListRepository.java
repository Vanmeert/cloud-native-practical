package com.ezgroceries.shoppinglist.db;

import com.ezgroceries.shoppinglist.db.entities.CocktailShoppingListEntity;

import java.util.List;
import java.util.UUID;

public interface CocktailShoppingListRepository extends BaseRepository<CocktailShoppingListEntity, CocktailShoppingListEntity> {

    List<CocktailShoppingListEntity> findByShoppingListId(UUID shoppingListId);

}
