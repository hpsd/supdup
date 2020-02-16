package com.deltaa.superduper.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deltaa.superduper.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ItemsListControllerIntegrationTest {

    private static final String SAVE_URL = "/itemsList";
    private static final String GET_URL = "/itemsList/1";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldSaveItemsList() throws Exception {

        mockMvc
            .perform(post(SAVE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.getItemsListJson()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)));

        mockMvc
            .perform(get(GET_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("shopping")))
            .andExpect(jsonPath("$.items[0].id", is(1)))
            .andExpect(jsonPath("$.items[0].name", is("apples")))
            .andExpect(jsonPath("$.items[0].tag", is("snack")))
            .andExpect(jsonPath("$.items[0].completed", is(false)))
            .andExpect(jsonPath("$.items[0].deleted", is(false)))
            .andExpect(jsonPath("$.items[0].reminders[0].id", is(1)))
            .andExpect(jsonPath("$.items[0].reminders[0].description", is("have an apple at 10 am")))
            .andExpect(jsonPath("$.items[0].reminders[1].id", is(2)))
            .andExpect(jsonPath("$.items[0].reminders[1].description", is("have an apple at 11 am")))
            .andExpect(jsonPath("$.items[1].id", is(2)))
            .andExpect(jsonPath("$.items[1].name", is("panadol")))
            .andExpect(jsonPath("$.items[1].tag", is("health")))
            .andExpect(jsonPath("$.items[1].completed", is(false)))
            .andExpect(jsonPath("$.items[1].deleted", is(false)));
    }

    @Test
    public void shouldGetBadRequestResponseWhenItemsListDoesNotExist() throws Exception {

        MvcResult result =
            mockMvc
                .perform(get("/itemsList/666"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("IllegalArgumentException: Items List not found", content);
    }

}
