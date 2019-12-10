package com.ezgroceries.shoppinglist.db;

import com.ezgroceries.shoppinglist.db.entities.ShoppingListEntity;

import java.util.UUID;

public interface ShoppingListRepository extends BaseRepository<ShoppingListEntity, UUID> {

    ShoppingListEntity findByName(String name);

}
