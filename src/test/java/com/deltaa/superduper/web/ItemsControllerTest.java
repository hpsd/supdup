package com.deltaa.superduper.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.deltaa.superduper.domain.entities.Reminder;
import com.deltaa.superduper.service.ItemService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@ContextConfiguration(classes = {ItemsController.class})
public class ItemsControllerTest {

    @Autowired
    private ItemsController underTest;

    @MockBean
    private ItemService itemsServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddRemindersToItem() throws IOException {

        List<Reminder> reminders = new ArrayList<>();

        underTest.addRemindersToItem(1, reminders);
        verify(itemsServiceMock, times(1)).addReminders(1, reminders);
    }

    @Test
    public void shouldUpdateItem() throws IOException {

        Optional<String> optionalData = Optional.of("data");
        String cmd = "cmd";

        underTest.updateItem(1, cmd, optionalData);

        verify(itemsServiceMock, times(1)).updateItem(1, cmd, optionalData);
    }

}
