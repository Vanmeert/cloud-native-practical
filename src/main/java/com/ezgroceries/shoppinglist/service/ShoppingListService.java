package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.db.entities.CocktailShoppingListEntity;
import com.ezgroceries.shoppinglist.db.CocktailShoppingListRepository;
import com.ezgroceries.shoppinglist.db.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.db.ShoppingListRepository;
import com.ezgroceries.shoppinglist.web.shoppinglists.ShoppingListResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    private final CocktailShoppingListRepository cocktailShoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailShoppingListRepository cocktailShoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailShoppingListRepository = cocktailShoppingListRepository;
    }

    public ShoppingListResource create(String name) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findByName(name);

        if (shoppingListEntity != null) {
            return null;
        }

        ShoppingListEntity newShoppingListEntity = new ShoppingListEntity();
        newShoppingListEntity.setId(UUID.randomUUID());
        newShoppingListEntity.setName(name);
        shoppingListEntity = shoppingListRepository.save(newShoppingListEntity);
        return new ShoppingListResource(
                shoppingListEntity.getId(),
                shoppingListEntity.getName(),
                new ArrayList<>()
        );
    }

    public List<UUID> mergeCocktailsShoppingLists(List<UUID> cocktailIds, UUID shoppingListId) {
        List<UUID> existingCocktailIds = cocktailShoppingListRepository.findByShoppingListId(shoppingListId)
                .stream()
                .map(CocktailShoppingListEntity::getCocktailId)
                .collect(Collectors.toList());

        return cocktailIds
                .stream()
                .filter(cocktailId -> !existingCocktailIds.contains(cocktailId))
                .map(cocktailId -> {
                    CocktailShoppingListEntity entity = new CocktailShoppingListEntity();
                    entity.setCocktailId(cocktailId);
                    entity.setShoppingListId(shoppingListId);
                    return cocktailShoppingListRepository.save(entity).getCocktailId();
                })
                .collect(Collectors.toList());
    }

    public Optional<ShoppingListResource> shoppingListForId(UUID shoppingListId) {
        Optional<ShoppingListEntity> shoppingListEntity = shoppingListRepository.findById(shoppingListId);
        return shoppingListEntity.map(entity -> new ShoppingListResource(
                entity.getId(),
                entity.getName(),
                new ArrayList<>()
        ));
    }

    public List<ShoppingListResource> allShoppingLists() {
        return shoppingListRepository
                .findAll()
                .stream()
                .map(entity -> new ShoppingListResource(
                        entity.getId(),
                        entity.getName(),
                        new ArrayList<>()
                ))
                .collect(Collectors.toList());
    }

    public List<UUID> cocktailIdsForShoppingListId(UUID shoppingListId) {
        List<CocktailShoppingListEntity> cocktailShoppingListEntities = cocktailShoppingListRepository.findByShoppingListId(shoppingListId);
        return cocktailShoppingListEntities
                .stream()
                .map(CocktailShoppingListEntity::getCocktailId)
                .collect(Collectors.toList());
    }

}
