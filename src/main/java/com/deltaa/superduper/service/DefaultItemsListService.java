package com.deltaa.superduper.service;

import com.deltaa.superduper.domain.entities.Item;
import com.deltaa.superduper.domain.entities.ItemsList;
import com.deltaa.superduper.domain.dao.ItemsListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultItemsListService implements ItemsListService {

    public static final String ITEMS_LIST_NOT_FOUND = "Items List not found";

    @Autowired
    private ItemsListRepository itemsListRepository;

    @Override
    public Optional<ItemsList> findById(long itemListsId) {
        return itemsListRepository.findById(itemListsId);
    }

    @Override
    public ItemsList save(ItemsList itemsList) {

        return itemsListRepository.save(itemsList);
    }

    @Transactional
    @Override
    public void addItems(long itemsListId, List<Item> items) {

        Optional<ItemsList> optionalItemsList = itemsListRepository.findById(itemsListId);

        ItemsList itemsList = optionalItemsList.orElseThrow(() -> new IllegalArgumentException(ITEMS_LIST_NOT_FOUND));

        itemsList.addItems(items);
    }

}
