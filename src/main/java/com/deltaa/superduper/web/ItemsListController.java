package com.deltaa.superduper.web;

import com.deltaa.superduper.domain.entities.Item;
import com.deltaa.superduper.domain.entities.ItemsList;
import com.deltaa.superduper.service.ItemsListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/itemsList")
public class ItemsListController {

    @Autowired
    private ItemsListService itemsListService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemsList> createList(@RequestBody @Valid final ItemsList itemsList) {

        ItemsList savedItemsList = itemsListService.save(itemsList);

        return new ResponseEntity<>(savedItemsList, HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping(value = "/{itemsListId}/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addItemsToList(@PathVariable long itemsListId,
                              @RequestBody @Size(min = 1) final List<Item> items) {

        itemsListService.addItems(itemsListId, items);
    }

    @GetMapping("/{itemsListId}")
    @ResponseBody
    public ItemsList getItemLists (@PathVariable long itemsListId) {
        return itemsListService.findById(itemsListId).get();
    }
}