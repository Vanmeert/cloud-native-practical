package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.service.CocktailService;
import com.ezgroceries.shoppinglist.web.cocktails.CocktailResource;
import com.ezgroceries.shoppinglist.web.cocktails.external.CocktailDBClient;
import com.ezgroceries.shoppinglist.web.cocktails.external.CocktailDBResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CocktailController {

    private CocktailService cocktailService;

    @Autowired
    CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping(value = "/cocktails")
    public @ResponseBody List<CocktailResource> get(@RequestParam String search) {
        return cocktailService.cocktails(search);
    }

}
