package com.github.hamideh6182.service;

import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookServiceTest {
    Book book1;
    BookRequest bookRequest1;
    BookRepository bookRepository;
    BookService bookService;
    IdService idService;

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
        bookRequest1 = new BookRequest(
                book1.title(),
                book1.author(),
                book1.description(),
                book1.copies(),
                book1.category(),
                book1.img()
        );
        bookRepository = mock(BookRepository.class);
        idService = mock(IdService.class);
        bookService = new BookService(bookRepository, idService);
    }

    @Test
    void getAllBooksTest() {
        //WHEN
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        //GIVEN
        List<Book> actual = bookService.getAllBooks();
        List<Book> expected = new ArrayList<>();
        //THEN
        verify(bookRepository).findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addBookTest() {
        //WHEN
        when(idService.generateId()).thenReturn("1");
        when(bookRepository.save(book1)).thenReturn(book1);
        //GIVEN
        Book actual = bookService.addBook(bookRequest1);
        Book expected = book1;
        //THEN
        verify(idService).generateId();
        verify(bookRepository).save(book1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addBook_WhenMissingTitle_ThenThrowsException() {
        //WHEN
        when(idService.generateId()).thenReturn("Whatever Id");
        //GIVEN
        BookRequest invalidBook = new BookRequest(
                null,
                book1.author(),
                book1.description(),
                book1.copies(),
                book1.category(),
                book1.img()
        );
        //THEN
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(invalidBook));
    }
}