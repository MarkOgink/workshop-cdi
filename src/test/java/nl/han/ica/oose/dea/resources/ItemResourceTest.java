package nl.han.ica.oose.dea.resources;

import nl.han.ica.oose.dea.services.ItemService;
import nl.han.ica.oose.dea.services.dto.ItemDTO;
import nl.han.ica.oose.dea.services.exceptions.IdAlreadyInUseException;
import nl.han.ica.oose.dea.services.exceptions.ItemNotAvailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.constraints.AssertTrue;
import javax.ws.rs.core.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemResourceTest {
    private static final int ITEM_ID = 1;
    private final String textItems = "bread, butter";
    private ItemResource sut = new ItemResource();
    private ItemService mockedItemService;

    @BeforeEach
    void setup() {
        this.sut = new ItemResource();

        // Gebruik Mockito om een instantie te maken
        this.mockedItemService = Mockito.mock(ItemService.class);

        // Gebruik de setter om de mockedItemService te zetten
        this.sut.setItemService(mockedItemService);
    }

    @Test
    void isGettingText() {
        //arrange
        //act
        String test = sut.getTextItems();
        //assert
        Assertions.assertEquals(textItems, test);
    }

    @Test
    void getJsonItemsCallsGetAll() {
        //arrange
        //act
        sut.getJsonItems();
        //assert
        Mockito.verify(mockedItemService).getAll();
    }

    @Test
    void getJsonReturnsObjectFromServiceAsEntity() {
        //arrange
        var itemsToReturn = new ArrayList<ItemDTO>();
        Mockito.when(mockedItemService.getAll()).thenReturn(itemsToReturn);
        //act
        var response = sut.getJsonItems();
        //assert
        assertEquals(200, response.getStatus());
        assertEquals(itemsToReturn, response.getEntity());
    }

    @Test
    void addItemCallsAddItemOnService() {
        //arrange
        ItemDTO itemDTO = new ItemDTO();
        //act
        sut.addItem(itemDTO);
        //assert
        Mockito.verify(mockedItemService).addItem(itemDTO);
    }

    @Test
    void addItemReturnsHttpCreated() {
        //arrange
        ItemDTO itemDTO = new ItemDTO();

        //act
        var response = sut.addItem(itemDTO);
        //assert
        assertEquals(201, response.getStatus());
    }

    @Test
    void addItemThrowsIdAlreadyInUseException() {
        //arrange
        var itemDTO = new ItemDTO(ITEM_ID, "Chocolate spread", new String[]{"Breakfast, Lunch"}, "Not to much");
        Mockito.doThrow(IdAlreadyInUseException.class).when(mockedItemService).addItem(itemDTO);
        //act
        //assert
        Assertions.assertThrows(IdAlreadyInUseException.class, () -> {
            sut.addItem(itemDTO);
        });
    }

    @Test
    void getItemCallsGetItemOnService() {
        //arrange
        var item = new ItemDTO(ITEM_ID, "Chocolate spread", new String[]{"Breakfast, Lunch"}, "Not to much");
        //act
        sut.getItem(ITEM_ID);
        //assert
        Mockito.verify(mockedItemService).getItem(ITEM_ID);
    }

    @Test
    void getItemThrowsItemNotAvailableException() {
        //arrange
        Mockito.doThrow(ItemNotAvailableException.class).when(mockedItemService).getItem(ITEM_ID);
        //act
        //assert
        Assertions.assertThrows(ItemNotAvailableException.class, () -> {
            sut.getItem(ITEM_ID);
        });
    }

    @Test
    void deleteItem() {
    }
}