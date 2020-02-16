package com.deltaa.superduper.service;

import com.deltaa.superduper.domain.dao.ItemRepository;
import com.deltaa.superduper.domain.entities.Item;
import com.deltaa.superduper.domain.entities.Reminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultItemService implements ItemService {

    public static final String EMPTY_TAG_ERROR = "Cannot apply empty tag value";
    public static final String ITEM_NOT_FOUND_ERROR = "Item not found";

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    @Override
    public void addReminders(long itemId, List<Reminder> reminders) {

        Optional<Item> optionalItem = itemRepository.findById(itemId);
        optionalItem.get().addReminders(reminders);
    }

    @Transactional
    @Override
    public void updateItem(long itemId, String cmd, Optional<String> data) {

        Item item = itemRepository.findById(itemId).orElseThrow(
            () -> new IllegalArgumentException(ITEM_NOT_FOUND_ERROR));

        updateItem(cmd, data, item);
    }

    private void updateItem(String cmd, Optional<String> data, Item item) {

        switch (ItemCommand.valueOf(cmd.toUpperCase())) {

            case COMPLETE:
                item.markComplete();
                break;

            case DELETE:
                item.markDeleted();
                break;

            case RESTORE:
                item.restore();
                break;

            case TAG:
                if (data == null || !data.isPresent()) {
                    throw new IllegalArgumentException(EMPTY_TAG_ERROR);
                }
                item.tag(data.get());
        }
    }

    private enum ItemCommand {

        COMPLETE,
        DELETE,
        RESTORE,
        TAG
    }

}
