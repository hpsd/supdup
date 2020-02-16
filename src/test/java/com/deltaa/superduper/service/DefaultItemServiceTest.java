package com.deltaa.superduper.service;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.deltaa.superduper.TestUtils;
import com.deltaa.superduper.domain.dao.ItemRepository;
import com.deltaa.superduper.domain.entities.Item;
import com.deltaa.superduper.domain.entities.Reminder;

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
@ContextConfiguration(classes = {DefaultItemService.class})
public class DefaultItemServiceTest {

    public static final String TAG = "aaaa";

    @Autowired
    private DefaultItemService underTest;

    @MockBean
    private ItemRepository itemRepositoryMock;

    @MockBean
    private Item itemMock;

    @Captor
    private ArgumentCaptor<List<Reminder>> remindersListArgCaptor;

    @Captor
    private ArgumentCaptor<String> tagArgCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddRemindersToItem() throws IOException {

        List<Reminder> reminders = TestUtils.getRemindersListObject();

        when(itemRepositoryMock.findById(1L)).thenReturn(Optional.of(itemMock));

        underTest.addReminders(1, reminders);

        verify(itemMock, times(1)).addReminders(remindersListArgCaptor.capture());
        assertSame(reminders, remindersListArgCaptor.getValue());
    }

    @Test
    public void shouldCompleteItem() {

        when(itemRepositoryMock.findById(1L)).thenReturn(Optional.of(itemMock));

        underTest.updateItem(1, "complete", null);

        verify(itemMock, times(1)).markComplete();
    }

    @Test
    public void shouldDeleteItem() {

        when(itemRepositoryMock.findById(1L)).thenReturn(Optional.of(itemMock));

        underTest.updateItem(1, "delete", null);

        verify(itemMock, times(1)).markDeleted();
    }

    @Test
    public void shouldRestoreItem() {

        when(itemRepositoryMock.findById(1L)).thenReturn(Optional.of(itemMock));

        underTest.updateItem(1, "restore", null);

        verify(itemMock, times(1)).restore();
    }

    @Test
    public void shouldTagItem() {

        when(itemRepositoryMock.findById(1L)).thenReturn(Optional.of(itemMock));

        underTest.updateItem(1, "tag", Optional.of(TAG));

        verify(itemMock, times(1)).tag(tagArgCaptor.capture());
        assertSame(TAG, tagArgCaptor.getValue());
    }

    @Test
    public void shouldThrowExceptionWhenRemindersListIsEmpty() {

        List<Reminder> reminders = new ArrayList<>();

        try {

            underTest.addReminders(1, reminders);
        } catch (IllegalArgumentException ex) {
            assertEquals(DefaultItemService.REMINDERS_ERROR, ex.getMessage());
        }
    }
    @Test
    public void shouldThrowExceptionWhenItemNotFoundForAddReminders() throws IOException {

        List<Reminder> reminders = TestUtils.getRemindersListObject();

        try {

            underTest.addReminders(1, reminders);
        } catch (IllegalArgumentException ex) {
            assertEquals("Item not found", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenItemNotFoundForUpdateItem() {

        try {

            underTest.updateItem(1, "tag", Optional.of("aaaa"));
        } catch (IllegalArgumentException ex) {
            assertEquals("Item not found", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenTagValueIsNotPresent() {

        when(itemRepositoryMock.findById(1L)).thenReturn(Optional.of(itemMock));

        try {

            underTest.updateItem(1, "tag", Optional.empty());
        } catch (IllegalArgumentException ex) {
            assertEquals(DefaultItemService.EMPTY_TAG_ERROR, ex.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenTagValueIsNull() {

        when(itemRepositoryMock.findById(1L)).thenReturn(Optional.of(itemMock));

        try {

            underTest.updateItem(1, "tag", null);
        } catch (IllegalArgumentException ex) {
            assertEquals(DefaultItemService.EMPTY_TAG_ERROR, ex.getMessage());
        }
    }
}
