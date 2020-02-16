package com.deltaa.superduper.service;

import com.deltaa.superduper.domain.entities.Reminder;

import java.util.List;
import java.util.Optional;

public interface ItemService {

     void addReminders(long itemId, List<Reminder> reminders);

     void updateItem(long itemId, String cmd, Optional<String> data);

}
