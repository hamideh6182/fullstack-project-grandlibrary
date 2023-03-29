package com.github.hamideh6182.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CsrfControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DirtiesContext
    void getCsrfToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/csrf"))
                .andExpect(status().isOk())
                .andExpect(content().string("CSRF token sent."));
    }
}