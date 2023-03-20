package com.github.hamideh6182.controller;

import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    BookRepository bookRepository;
    Book book1;

    @BeforeEach
    void setUp() {
        book1 = new Book(
                "1",
                "JavaBook",
                "Hamideh Aghdam",
                "About Java",
                10,
                10,
                "Programming",
                "http://imgage.com/img1.png"
        );
    }

    @Test
    @DirtiesContext
    void getAllBooksTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllBooks_WhenOneBookInRepo_ThenReturnListOfOneBook() throws Exception {
        bookRepository.save(book1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                                                {
                                                "id" : "1",
                                                "title" : "JavaBook",
                                                "author" : "Hamideh Aghdam",
                                                "description" : "About Java",
                                                "copies" : 10,
                                                "copiesAvailable" : 10,
                                                "category" : "Programming",
                                                "img" : "http://imgage.com/img1.png"
                                                }
                        ]
                        """));
    }

    @Test
    @DirtiesContext
    void addBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                                        "title" : "JavaBook",
                                                        "author" : "Hamideh Aghdam",
                                                        "description" : "About Java",
                                                        "copies" : 10,
                                                        "category" : "Programming",
                                                        "img" : "http://imgage.com/img1.png"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                                        "title" : "JavaBook",
                                                        "author" : "Hamideh Aghdam",
                                                        "description" : "About Java",
                                                        "copies" : 10,
                                                        "copiesAvailable" : 10,
                                                        "category" : "Programming",
                                                        "img" : "http://imgage.com/img1.png"
                                }
                                           """
                )).andExpect(jsonPath("$.id").isNotEmpty());
    }
}