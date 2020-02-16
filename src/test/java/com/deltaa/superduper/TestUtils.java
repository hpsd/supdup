package com.deltaa.superduper;

import com.deltaa.superduper.domain.entities.ItemsList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.util.List;

public class TestUtils {

    public static String getItemsListJson() {
        return new String(getResourceFile("/items_list.json"));
    }

    public static String getRemindersListJson() {
        return new String(getResourceFile("/reminders_list.json"));
    }

    public static ItemsList getItemsListModel() throws IOException {

        return new ObjectMapper().readValue(getItemsListJson(), ItemsList.class);
    }

    public static List getRemindersListObject() throws IOException {

        return new ObjectMapper().readValue(getRemindersListJson(), List.class);
    }

    private static byte[] getResourceFile(final String file) throws Error {
        try {
            return ByteStreams.toByteArray(TestUtils.class.getResourceAsStream(file));
        } catch (Exception exc) {
            throw new Error(exc);
        }
    }

}
