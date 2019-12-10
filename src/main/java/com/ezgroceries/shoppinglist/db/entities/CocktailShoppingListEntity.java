package com.ezgroceries.shoppinglist.db.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@IdClass(CocktailShoppingListEntity.class)
@Table(name = "cocktail_shopping_list")
public class CocktailShoppingListEntity implements Serializable {

    @Id
    @Column(name="cocktail_id")
    private UUID cocktailId;

    @Id
    @Column(name="shopping_list_id")
    private UUID shoppingListId;

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
}
