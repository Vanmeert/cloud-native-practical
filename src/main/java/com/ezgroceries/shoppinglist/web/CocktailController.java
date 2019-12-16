package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.service.CocktailService;
import com.ezgroceries.shoppinglist.web.dto.cocktails.CocktailResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping(value = "/cocktails")
    public @ResponseBody List<CocktailResource> get(@RequestParam String search) {
        return cocktailService.cocktails(search);
    }

}
