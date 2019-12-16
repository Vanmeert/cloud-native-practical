package com.ezgroceries.shoppinglist.db;

import com.ezgroceries.shoppinglist.db.entities.CocktailShoppingListEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CocktailShoppingListRepository extends CrudRepository<CocktailShoppingListEntity, CocktailShoppingListEntity> {

    List<CocktailShoppingListEntity> findByShoppingListId(UUID shoppingListId);

}
