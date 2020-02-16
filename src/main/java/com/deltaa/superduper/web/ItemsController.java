package com.deltaa.superduper.web;

import com.deltaa.superduper.domain.entities.Reminder;
import com.deltaa.superduper.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @PostMapping(value = "/{itemId}/reminders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addRemindersToItem(@PathVariable long itemId,
                                   @RequestBody @Size(min = 1) final List<Reminder> reminders) {

        itemService.addReminders(itemId, reminders);
    }

    @PutMapping("/{itemId}/{cmd}")
    public void updateItem(@PathVariable long itemId, @PathVariable String cmd, @RequestParam Optional<String> data) {

        itemService.updateItem(itemId, cmd, data);
    }

}