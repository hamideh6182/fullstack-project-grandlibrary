package com.github.hamideh6182.service;

import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class BookServiceTest {
    Book book1;
    BookRepository bookRepository;
    BookService bookService;

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
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void getAllBooksTest() {
        //Given
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        //WHEN
        List<Book> actual = bookService.getAllBooks();
        List<Book> expected = new ArrayList<>();
        //THEN
        verify(bookRepository).findAll();
        Assertions.assertEquals(expected, actual);
    }
}
