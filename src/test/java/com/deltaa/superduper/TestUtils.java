package com.deltaa.superduper;

import com.deltaa.superduper.domain.entities.ItemsList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;

import java.io.IOException;

public class TestUtils {

    public static String getItemsListJson() {
        return new String(getResourceFile("/items_list.json"));
    }

    public static ItemsList getItemsListModel() throws IOException {

        return new ObjectMapper().readValue(getItemsListJson(), ItemsList.class);
    }

    private static byte[] getResourceFile(final String file) throws Error {
        try {
            return ByteStreams.toByteArray(TestUtils.class.getResourceAsStream(file));
        } catch (Exception exc) {
            throw new Error(exc);
        }
    }

}
