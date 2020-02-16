package com.deltaa.superduper.service;

import com.deltaa.superduper.domain.entities.Item;
import com.deltaa.superduper.domain.entities.ItemsList;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

public interface ItemsListService {

     Optional<ItemsList> findById(long itemListsId);

     ItemsList save(@Valid ItemsList itemsList);

     void addItems(long itemsListId, List<Item> items);

}
