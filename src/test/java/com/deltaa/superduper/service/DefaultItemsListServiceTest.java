package com.deltaa.superduper.service;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.deltaa.superduper.TestUtils;
import com.deltaa.superduper.domain.dao.ItemsListRepository;
import com.deltaa.superduper.domain.entities.Item;
import com.deltaa.superduper.domain.entities.ItemsList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DefaultItemsListService.class})
public class DefaultItemsListServiceTest {

    public static final String TAG = "aaaa";
    public static final String EMPTY_TAG_ERROR = "Cannot apply empty tag value";

    @Autowired
    private DefaultItemsListService underTest;

    @MockBean
    private ItemsListRepository itemListRepositoryMock;

    @MockBean
    private ItemsList itemsListMock;

    @Captor
    private ArgumentCaptor<List<Item>> itemsListArgCaptor;

    @Captor
    private ArgumentCaptor<String> tagArgCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindById() throws IOException {

        Optional<ItemsList> optionalItemsList = Optional.empty();

        when(itemListRepositoryMock.findById(1L)).thenReturn(optionalItemsList);

        Optional<ItemsList> returnValue = underTest.findById(1);

        assertSame(optionalItemsList, returnValue);
    }

    @Test
    public void shouldSaveItemsList() throws IOException {

        ItemsList itemsList = TestUtils.getItemsListModel();

        underTest.save(itemsList);

        verify(itemListRepositoryMock, times(1)).save(itemsList);

    }

    @Test
    public void shouldAddItemsToItemsList() {

        List<Item> items = new ArrayList<Item>();

        when(itemListRepositoryMock.findById(1L)).thenReturn(Optional.of(itemsListMock));

        underTest.addItems(1, items);

        verify(itemsListMock, times(1)).addItems(itemsListArgCaptor.capture());
        assertSame(items, itemsListArgCaptor.getValue());
    }

    @Test
    public void shouldThrowExceptionWhenItemsListNotFound() {

        List<Item> items = new ArrayList<Item>();

        try {

            underTest.addItems(1, items);
        } catch (IllegalArgumentException ex) {
            assertEquals(DefaultItemsListService.ITEMS_LIST_NOT_FOUND, ex.getMessage());
        }
    }
}
