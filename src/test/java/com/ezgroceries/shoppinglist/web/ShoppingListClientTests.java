package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.shoppinglists.CreateShoppingListInput;
import com.ezgroceries.shoppinglist.shoppinglists.CreateShoppingListOutput;
import com.ezgroceries.shoppinglist.shoppinglists.ShoppingListAddCocktailDTO;
import com.ezgroceries.shoppinglist.shoppinglists.ShoppingListResource;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingListClientTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createNewList() {
        String url = "/shopping-lists";

        CreateShoppingListInput request = new CreateShoppingListInput("Test");
        ResponseEntity<CreateShoppingListOutput> response = restTemplate.postForEntity(url, request, CreateShoppingListOutput.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        CreateShoppingListOutput shoppingList = response.getBody();
        assertNotNull(shoppingList.getShoppingListId());
        assertEquals(request.getName(), shoppingList.getName());
    }

    @Test
    public void addCocktailToList() {
        String url = "/shopping-lists/{uuid}/cocktails";

        ShoppingListAddCocktailDTO request[] = new ShoppingListAddCocktailDTO[] {
                new ShoppingListAddCocktailDTO(UUID.randomUUID())
        };
        ResponseEntity<ShoppingListAddCocktailDTO[]> response = restTemplate.postForEntity(url, request, ShoppingListAddCocktailDTO[].class, UUID.randomUUID());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        ShoppingListAddCocktailDTO[] addedCocktails = response.getBody();
        assertEquals(request.length, addedCocktails.length);
        assertEquals(request[0].getCocktailId(), addedCocktails[0].getCocktailId());
    }

    @Test
    public void getShoppingList() {
        String url = "/shopping-lists/{uuid}";

        ResponseEntity<ShoppingListResource> response = restTemplate.getForEntity(url, ShoppingListResource.class, UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        ShoppingListResource shoppingList = response.getBody();
        assertEquals(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"), shoppingList.getShoppingListId());
        assertEquals("Stephanie's Birthday", shoppingList.getName());

        List<String> ingredients = shoppingList.getIngredients();
        List<String> expectedIngredients = Arrays.asList(
                "Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao"
        );
        assertEquals(expectedIngredients, ingredients);
    }

    @Test
    public void getShoppingLists() {
        String url = "/shopping-lists";

        ResponseEntity<ShoppingListResource[]> response = restTemplate.getForEntity(url, ShoppingListResource[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        ShoppingListResource[] shoppingLists = response.getBody();
        assertNotNull(shoppingLists);
        assertEquals(2, shoppingLists.length);

        ShoppingListResource firstShoppingList = shoppingLists[0];
        assertEquals(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"), firstShoppingList.getShoppingListId());
        assertEquals("Stephanie's Birthday", firstShoppingList.getName());

        List<String> ingredients = firstShoppingList.getIngredients();
        List<String> expectedIngredients = Arrays.asList(
                "Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao"
        );
        assertEquals(expectedIngredients, ingredients);
    }

}
