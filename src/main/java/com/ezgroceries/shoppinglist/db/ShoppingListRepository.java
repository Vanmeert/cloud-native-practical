package com.ezgroceries.shoppinglist.db;

import java.util.UUID;

public interface ShoppingListRepository extends BaseRepository<ShoppingListEntity, UUID> {

    ShoppingListEntity findByName(String name);

}
