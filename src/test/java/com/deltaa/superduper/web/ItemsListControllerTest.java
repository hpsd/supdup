package com.deltaa.superduper.web;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.deltaa.superduper.TestUtils;
import com.deltaa.superduper.domain.entities.Item;
import com.deltaa.superduper.domain.entities.ItemsList;
import com.deltaa.superduper.service.ItemsListService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ItemsListController.class})
public class ItemsListControllerTest {

    @Autowired
    private ItemsListController underTest;

    @MockBean
    private ItemsListService itemsListServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateItemsList() throws IOException {

        ItemsList itemsList = TestUtils.getItemsListModel();

        when(itemsListServiceMock.save(itemsList)).thenReturn(itemsList);

        ResponseEntity<ItemsList> responseEntity = underTest.createList(itemsList);

        verify(itemsListServiceMock, times(1)).save(itemsList);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertSame(itemsList, responseEntity.getBody());
    }

    @Test
    public void shouldAddItems() throws IOException {

        List<Item> items = new ArrayList<>();
        underTest.addItemsToList(1, items);

        verify(itemsListServiceMock, times(1)).addItems(1, items);
    }

    @Test
    public void shouldFindById() throws IOException {

        underTest.getItemLists(1);

        verify(itemsListServiceMock, times(1)).findById(1);
    }

}
