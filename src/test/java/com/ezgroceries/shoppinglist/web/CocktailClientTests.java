package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.cocktails.CocktailResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CocktailClientTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void listCocktails() {
        String url = "/cocktails?search=Russian";

        ResponseEntity<CocktailResource[]> response = restTemplate.getForEntity(url, CocktailResource[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        CocktailResource[] cocktails = response.getBody();
        assertNotNull(cocktails);
        assertEquals(2, cocktails.length);

        CocktailResource firstCocktail = cocktails[0];
        assertEquals(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"), firstCocktail.getCocktailId());
        assertEquals("Margerita", firstCocktail.getName());
        assertEquals("Cocktail glass", firstCocktail.getGlass());
        assertEquals("Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..", firstCocktail.getInstructions());
        assertEquals("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg", firstCocktail.getImage());

        List<String> ingredients = firstCocktail.getIngredients();
        List<String> expectedIngredients = Arrays.asList(
                "Tequila",
                "Triple sec",
                "Lime juice",
                "Salt"
        );
        assertEquals(expectedIngredients, ingredients);
    }
}
