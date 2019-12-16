package com.ezgroceries.shoppinglist.db;

import com.ezgroceries.shoppinglist.db.entities.ShoppingListEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShoppingListRepository extends CrudRepository<ShoppingListEntity, UUID> {

    ShoppingListEntity findByName(String name);

}
