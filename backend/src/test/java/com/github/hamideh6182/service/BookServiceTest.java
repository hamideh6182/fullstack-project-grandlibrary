package com.github.hamideh6182.service;

import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookServiceTest {
    Book book1;
    BookRequest bookRequest1;
    BookRepository bookRepository;
    BookService bookService;
    IdService idService;
    PhotoService photoService;
    MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        bookRequest1 = new BookRequest(
                "JavaBook",
                "Hamideh Aghdam",
                "About Java",
                10,
                "Programming"
        );
        book1 = new Book(
                "1",
                "JavaBook",
                "Hamideh Aghdam",
                "About Java",
                10,
                10,
                "Programming",
                "http:photo.com"
        );
        bookRepository = mock(BookRepository.class);
        idService = mock(IdService.class);
        photoService = mock(PhotoService.class);
        multipartFile = mock(MultipartFile.class);
        bookService = new BookService(bookRepository, idService, photoService);
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
    void getBookByIdTest_WhenBookExist() {
        //WHEN
        when(bookRepository.findById("1")).thenReturn(Optional.of(book1));
        //GIVEN
        Book actual = bookService.getBookById("1");
        Book expected = book1;
        //THEN
        verify(bookRepository).findById("1");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getBookByIdTest_WhenBookDoesNotExist_ThenThrowException() {
        //WHEN
        when(bookRepository.findById("2")).thenReturn(Optional.empty());
        //THEN
        assertThrows(NoSuchElementException.class, () -> bookService.getBookById("2"));
        verify(bookRepository).findById("2");
    }

    @Test
    void addBookTest() throws IOException {
        //WHEN
        when(idService.generateId()).thenReturn("1");
        when(bookRepository.save(book1)).thenReturn(book1);
        when(photoService.uploadPhoto(multipartFile)).thenReturn(book1.img());
        //GIVEN
        Book actual = bookService.addBook(bookRequest1, multipartFile);
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
                book1.category()
        );
        //THEN
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(invalidBook, multipartFile));
    }
}