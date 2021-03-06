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
    public static final String REMINDERS_ERROR = "Reminders list is empty";

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    @Override
    public void addReminders(long itemId, List<Reminder> reminders) {

        if (reminders == null || reminders.size() == 0) {
            throw new IllegalArgumentException(REMINDERS_ERROR);
        }

        findItemById(itemId).addReminders(reminders);
    }

    @Transactional
    @Override
    public void updateItem(long itemId, String cmd, Optional<String> data) {

        updateItem(cmd, data, findItemById(itemId));
    }

    private Item findItemById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException(ITEM_NOT_FOUND_ERROR));
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
