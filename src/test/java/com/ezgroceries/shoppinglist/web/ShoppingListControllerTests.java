package com.ezgroceries.shoppinglist.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.service.CocktailService;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.ezgroceries.shoppinglist.web.dto.shoppinglists.CreateShoppingListInput;
import com.ezgroceries.shoppinglist.web.dto.shoppinglists.ShoppingListAddCocktailDTO;
import com.ezgroceries.shoppinglist.web.dto.shoppinglists.ShoppingListResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoppingListController.class)
public class ShoppingListControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService;

    @MockBean
    private CocktailService cocktailService;

    @Test
    public void createNewList() throws Exception {
        ShoppingListResource expectedResult = new ShoppingListResource(UUID.randomUUID(), "Test", Collections.emptyList());

        given(shoppingListService.create("Test")).willReturn(expectedResult);

        String url = "/shopping-lists";
        CreateShoppingListInput request = new CreateShoppingListInput("Test");
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shoppingListId").value(expectedResult.getShoppingListId().toString()))
                .andExpect(jsonPath("$.name").value(expectedResult.getName()));

        verify(shoppingListService).create("Test");
    }

    @Test
    public void addCocktailToList() throws Exception {
        UUID shoppingListId = UUID.randomUUID();
        List<UUID> expectedCocktailIds = Collections.singletonList(UUID.randomUUID());

        given(shoppingListService.mergeCocktailsShoppingLists(expectedCocktailIds, shoppingListId)).willReturn(expectedCocktailIds);

        String url = "/shopping-lists/{uuid}/cocktails";
        ShoppingListAddCocktailDTO[] request = expectedCocktailIds
                .stream()
                .map(ShoppingListAddCocktailDTO::new)
                .collect(Collectors.toList())
                .toArray(new ShoppingListAddCocktailDTO[expectedCocktailIds.size()]);
        mockMvc.perform(post(url, shoppingListId).contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(expectedCocktailIds.size())))
                .andExpect(jsonPath("$[0].cocktailId").value(expectedCocktailIds.get(0).toString()));

        verify(shoppingListService).mergeCocktailsShoppingLists(expectedCocktailIds, shoppingListId);
    }

    @Test
    public void getShoppingList() throws Exception {
        UUID shoppingListId = UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f");
        List<UUID> cocktailIds = Collections.singletonList(UUID.randomUUID());
        ShoppingListResource expectedShoppingList = getDummyShoppingLists().get(0);
        List<String> expectedIngredients = getDummyIngredients();

        given(shoppingListService.shoppingListForId(shoppingListId)).willReturn(Optional.of(expectedShoppingList));
        given(shoppingListService.cocktailIdsForShoppingListId(shoppingListId)).willReturn(cocktailIds);
        given(cocktailService.cocktailIngredients(cocktailIds)).willReturn(expectedIngredients);

        String url = "/shopping-lists/{uuid}";
        mockMvc.perform(get(url, shoppingListId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shoppingListId").value(expectedShoppingList.getShoppingListId().toString()))
                .andExpect(jsonPath("$.name").value(expectedShoppingList.getName()))
                .andExpect(jsonPath("$.ingredients").isArray())
                .andExpect(jsonPath("$.ingredients", hasSize(expectedIngredients.size())));

        verify(shoppingListService).shoppingListForId(shoppingListId);
        verify(shoppingListService).cocktailIdsForShoppingListId(shoppingListId);
        verify(cocktailService).cocktailIngredients(cocktailIds);
    }

    @Test
    public void getShoppingLists() throws Exception {
        List<ShoppingListResource> expectedShoppingLists = getDummyShoppingLists();
        List<UUID> cocktailIds = Collections.singletonList(UUID.randomUUID());
        List<String> expectedIngredients = getDummyIngredients();

        given(shoppingListService.allShoppingLists()).willReturn(expectedShoppingLists);
        given(shoppingListService.cocktailIdsForShoppingListId(expectedShoppingLists.get(0).getShoppingListId())).willReturn(cocktailIds);
        given(shoppingListService.cocktailIdsForShoppingListId(expectedShoppingLists.get(1).getShoppingListId())).willReturn(cocktailIds);
        given(cocktailService.cocktailIngredients(cocktailIds)).willReturn(expectedIngredients);

        String url = "/shopping-lists";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(expectedShoppingLists.size())))
                .andExpect(jsonPath("$[0].shoppingListId").value(expectedShoppingLists.get(0).getShoppingListId().toString()))
                .andExpect(jsonPath("$[0].name").value(expectedShoppingLists.get(0).getName()))
                .andExpect(jsonPath("$[0].ingredients").isArray())
                .andExpect(jsonPath("$[0].ingredients", hasSize(expectedIngredients.size())));

        verify(shoppingListService).allShoppingLists();
        verify(shoppingListService).cocktailIdsForShoppingListId(expectedShoppingLists.get(0).getShoppingListId());
        verify(shoppingListService).cocktailIdsForShoppingListId(expectedShoppingLists.get(1).getShoppingListId());
        verify(cocktailService, times(2)).cocktailIngredients(cocktailIds);
    }

    private List<ShoppingListResource> getDummyShoppingLists() {
        return Arrays.asList(
                new ShoppingListResource(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"),
                        "Stephanie's Birthday",
                        Collections.emptyList()),
                new ShoppingListResource(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"),
                        "My Birthday",
                        Collections.emptyList())
        );
    }

    private List<String> getDummyIngredients() {
        return Arrays.asList(
                "Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao"
        );
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
