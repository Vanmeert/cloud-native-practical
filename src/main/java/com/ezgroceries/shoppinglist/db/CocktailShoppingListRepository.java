package com.ezgroceries.shoppinglist.db;

import java.util.List;
import java.util.UUID;

public interface CocktailShoppingListRepository extends BaseRepository<CocktailShoppingListEntity, CocktailShoppingListEntity> {

    List<CocktailShoppingListEntity> findByShoppingListId(UUID shoppingListId);

}
