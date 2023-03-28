package com.github.hamideh6182.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.MongoUser;
import com.github.hamideh6182.repository.BookRepository;
import com.github.hamideh6182.repository.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    BookRepository bookRepository;
    @MockBean
    Cloudinary cloudinary;
    Uploader uploader = mock(Uploader.class);
    Book book1;
    @Autowired
    MongoUserRepository mongoUserRepository;
    MongoUser mongoUser;

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
                "http://imgage.com/img1.png",
                "1a"
        );
        mongoUser = new MongoUser("1a", "user", "password", "BASIC");
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
                                                "img" : "http://imgage.com/img1.png",
                                                "userId" : "1a"
                                                }
                        ]
                        """));
    }

    @Test
    @DirtiesContext
    void getBookByIdTest() throws Exception {
        bookRepository.save(book1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                {
                                                "id" : "1",
                                                "title" : "JavaBook",
                                                "author" : "Hamideh Aghdam",
                                                "description" : "About Java",
                                                "copies" : 10,
                                                "copiesAvailable" : 10,
                                                "category" : "Programming",
                                                "img" : "http://imgage.com/img1.png",
                                                "userId" : "1a"
                                                }
                        """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void addBookTest() throws Exception {
        mongoUserRepository.save(mongoUser);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), anyMap())).thenReturn(Map.of("url", "http://imgage.com/img1.png"));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/books")
                        .file(new MockMultipartFile("bookRequest", null, "application/json",
                                """
                                        {
                                                 "title" : "JavaBook",
                                                 "author" : "Hamideh Aghdam",
                                                 "description" : "About Java",
                                                 "copies" : 10,
                                                 "category" : "Programming",
                                                 "userId" : "1a"
                                        }
                                        """.getBytes()))
                        .file(new MockMultipartFile("file", "content".getBytes()))
                        .with(csrf())
                )
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
                                                 "img" : "http://imgage.com/img1.png",
                                                 "userId" : "1a"
                                }
                                           """
                )).andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteBook_WhenIdExist_ThenReturnBook() throws Exception {
        mongoUserRepository.save(mongoUser);
        bookRepository.save(book1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                                                    "id" : "1",
                                                    "title" : "JavaBook",
                                                    "author" : "Hamideh Aghdam",
                                                    "description" : "About Java",
                                                    "copies" : 10,
                                                    "copiesAvailable" : 10,
                                                    "category" : "Programming",
                                                    "img" : "http://imgage.com/img1.png",
                                                    "userId" : "1a"
                        }
                                         """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void increaseBookQuantityTest_WhenQuantityPlusOne() throws Exception {
        mongoUserRepository.save(mongoUser);
        bookRepository.save(book1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/quantity/increase/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                                     "id" : "1",
                                                     "title" : "JavaBook",
                                                     "author" : "Hamideh Aghdam",
                                                     "description" : "About Java",
                                                     "copies" : 10,
                                                     "copiesAvailable" : 10,
                                                     "category" : "Programming",
                                                     "img" : "http://imgage.com/img1.png",
                                                     "userId" : "1a"
                                }
                                                         """)
                        .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                                                      "id" : "1",
                                                      "title" : "JavaBook",
                                                      "author" : "Hamideh Aghdam",
                                                      "description" : "About Java",
                                                      "copies" : 11,
                                                      "copiesAvailable" : 11,
                                                      "category" : "Programming",
                                                      "img" : "http://imgage.com/img1.png",
                                                      "userId" : "1a"
                        }
                                                """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void decreaseBookQuantityTest_WhenQuantityMinusOne() throws Exception {
        mongoUserRepository.save(mongoUser);
        bookRepository.save(book1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/quantity/decrease/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                                        "id" : "1",
                                                        "title" : "JavaBook",
                                                        "author" : "Hamideh Aghdam",
                                                        "description" : "About Java",
                                                        "copies" : 10,
                                                        "copiesAvailable" : 10,
                                                        "category" : "Programming",
                                                        "img" : "http://imgage.com/img1.png",
                                                        "userId" : "1a"
                                }
                                                         """)
                        .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                                                          "id" : "1",
                                                          "title" : "JavaBook",
                                                          "author" : "Hamideh Aghdam",
                                                          "description" : "About Java",
                                                          "copies" : 9,
                                                          "copiesAvailable" : 9,
                                                          "category" : "Programming",
                                                          "img" : "http://imgage.com/img1.png",
                                                          "userId" : "1a"
                        }
                                                """));
    }
}