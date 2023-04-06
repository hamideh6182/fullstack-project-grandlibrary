package com.github.hamideh6182.service;

import com.github.hamideh6182.exception.BookNotFoundException;
import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.model.Checkout;
import com.github.hamideh6182.model.MongoUserResponse;
import com.github.hamideh6182.repository.BookRepository;
import com.github.hamideh6182.repository.CheckoutRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    Book book1;
    Book book2;
    Book book3;
    Book book4;
    Book checkoutBook1;
    BookRequest bookRequest1;
    Checkout checkout1;
    BookRepository bookRepository;
    BookService bookService;
    IdService idService;
    PhotoService photoService;
    MultipartFile multipartFile;
    MongoUserDetailsService mongoUserDetailsService;
    CheckoutRepository checkoutRepository;
    Principal principal;

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
                "http:photo.com",
                "1a"
        );
        book2 = new Book(
                "1",
                "JavaBook",
                "Hamideh Aghdam",
                "About Java",
                11,
                11,
                "Programming",
                "http:photo.com",
                "1a"
        );
        book3 = new Book(
                "1",
                "JavaBook",
                "Hamideh Aghdam",
                "About Java",
                9,
                9,
                "Programming",
                "http:photo.com",
                "1a"
        );
        book4 = new Book(
                "1",
                "JavaBook",
                "Hamideh Aghdam",
                "About Java",
                0,
                0,
                "Programming",
                "http:photo.com",
                "1a"
        );
        checkoutBook1 = new Book(
                "1",
                "JavaBook",
                "Hamideh Aghdam",
                "About Java",
                10,
                9,
                "Programming",
                "http:photo.com",
                "1a"
        );
        checkout1 = new Checkout(
                "1",
                "1",
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                "1a"
        );
        bookRepository = mock(BookRepository.class);
        idService = mock(IdService.class);
        mongoUserDetailsService = mock(MongoUserDetailsService.class);
        principal = mock(Principal.class);
        photoService = mock(PhotoService.class);
        multipartFile = mock(MultipartFile.class);
        checkoutRepository = mock(CheckoutRepository.class);
        bookService = new BookService(bookRepository, idService, photoService, mongoUserDetailsService, checkoutRepository);
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
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //GIVEN
        Book actual = bookService.addBook(bookRequest1, multipartFile, principal);
        Book expected = book1;
        //THEN
        verify(idService).generateId();
        verify(bookRepository).save(book1);
        verify(mongoUserDetailsService).getMe(principal);
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
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(invalidBook, multipartFile, principal));
    }

    @Test
    void deleteBook_whenBookDoesntExist_thenThrowException() {
        //WHEN
        when(bookRepository.findById("4")).thenReturn(Optional.empty());
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //THEN
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook("4", principal));
        verify(bookRepository).findById("4");
        verify(mongoUserDetailsService).getMe(principal);
    }

    @Test
    void deleteBook_whenBookExists_thenReturnBook() {
        //WHEN
        when(bookRepository.findById("1")).thenReturn(Optional.ofNullable(book1));
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //GIVEN
        Book actual = bookService.deleteBook(book1.id(), principal);
        Book expected = book1;
        //THEN
        verify(bookRepository).findById("1");
        verify(mongoUserDetailsService).getMe(principal);
        assertEquals(expected, actual);
    }

    @Test
    void increaseBookQuantityTest_WhenQuantityPlusOne() {
        //WHEN
        when(bookRepository.findById("1")).thenReturn(Optional.of(book1));
        when(bookRepository.save(book2)).thenReturn(book2);
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //GIVEN
        Book actual = bookService.increaseBookQuantity(book1.id(), principal);
        Book expected = book2;
        //THEN
        verify(bookRepository).findById("1");
        verify(bookRepository).save(book2);
        verify(mongoUserDetailsService).getMe(principal);
        assertEquals(expected, actual);
    }

    @Test
    void increaseBookQuantityTest_whenBookDoesntExist_thenThrowException() {
        //WHEN
        when(bookRepository.findById("4")).thenReturn(Optional.empty());
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //THEN
        assertThrows(BookNotFoundException.class, () -> bookService.increaseBookQuantity("4", principal));
        verify(bookRepository).findById("4");
        verify(mongoUserDetailsService).getMe(principal);
    }

    @Test
    void decreaseBookQuantityTest_WhenQuantityMinusOne() {
        //WHEN
        when(bookRepository.findById("1")).thenReturn(Optional.of(book1));
        when(bookRepository.save(book3)).thenReturn(book3);
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //GIVEN
        Book actual = bookService.decreaseBookQuantity(book1.id(), principal);
        Book expected = book3;
        //THEN
        verify(bookRepository).findById("1");
        verify(bookRepository).save(book3);
        verify(mongoUserDetailsService).getMe(principal);
        assertEquals(expected, actual);
    }

    @Test
    void decreaseBookQuantityTest_whenBookDoesntExist_thenThrowException() {
        //WHEN
        when(bookRepository.findById("4")).thenReturn(Optional.empty());
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //THEN
        assertThrows(BookNotFoundException.class, () -> bookService.decreaseBookQuantity("4", principal));
        verify(bookRepository).findById("4");
        verify(mongoUserDetailsService).getMe(principal);
    }

    @Test
    void decreaseBookQuantityTest_whenBookExist_CopiesZero_thenThrowException() {
        //WHEN
        when(bookRepository.findById("4")).thenReturn(Optional.of(book4));
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //THEN
        assertThrows(BookNotFoundException.class, () -> bookService.decreaseBookQuantity("4", principal));
        verify(bookRepository).findById("4");
        verify(mongoUserDetailsService).getMe(principal);
    }

    @Test
    void checkoutBook_withValidInput_shouldReturnNewBook() {
        //WHEN
        when(idService.generateId()).thenReturn("1");
        when(bookRepository.findById("1")).thenReturn(Optional.of(book1));
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        when(checkoutRepository.findByUserIdAndBookId("1a", "1")).thenReturn(null);
        when(bookRepository.save(checkoutBook1)).thenReturn(checkoutBook1);
        when(checkoutRepository.save(checkout1)).thenReturn(checkout1);
        //GIVEN
        Book actual = bookService.checkoutBook("1a", "1", principal);
        Book expected = checkoutBook1;
        //THEN
        verify(idService).generateId();
        verify(bookRepository).findById("1");
        verify(mongoUserDetailsService).getMe(principal);
        verify(checkoutRepository).findByUserIdAndBookId("1a", "1");
        verify(bookRepository).save(checkoutBook1);
        verify(checkoutRepository).save(checkout1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkoutBook_withInvalidBookId_shouldThrowBookNotFoundException() {
        //WHEN
        when(bookRepository.findById("4")).thenReturn(Optional.empty());
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        //THEN
        assertThrows(BookNotFoundException.class, () -> bookService.checkoutBook("1a", "4", principal));
        verify(bookRepository).findById("4");
        verify(mongoUserDetailsService).getMe(principal);
    }

    @Test
    void checkoutBook_withAlreadyCheckedOutBook_shouldThrowBookNotFoundException() {
        Checkout existingCheckout = checkout1;
        //WHEN
        when(bookRepository.findById("1")).thenReturn(Optional.of(book1));
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        when(checkoutRepository.findByUserIdAndBookId("1a", "1")).thenReturn(existingCheckout);
        //THEN
        assertThrows(BookNotFoundException.class, () -> bookService.checkoutBook("1a", "1", principal));
        verify(bookRepository).findById("1");
        verify(mongoUserDetailsService).getMe(principal);
        verify(checkoutRepository).findByUserIdAndBookId("1a", "1");
    }

    @Test
    void testCheckoutBookByUser() {
        //When
        when(checkoutRepository.findByUserIdAndBookId("1a", "1")).thenReturn(checkout1);
        //GIVEN
        boolean actual = bookService.checkoutBookByUser("1a", "1");
        //THEN
        verify(checkoutRepository).findByUserIdAndBookId("1a", "1");
        assertTrue(actual);
    }

    @Test
    void testCheckoutBookByUserInvalid() {
        //WHEN
        when(checkoutRepository.findByUserIdAndBookId("2a", "1")).thenReturn(null);
        //GIVEN
        boolean actual = bookService.checkoutBookByUser("2a", "1");
        verify(checkoutRepository).findByUserIdAndBookId("2a", "1");
        assertFalse(actual);
    }

    @Test
    void testReturnBook_ThenReturnBook() {
        //WHEN
        when(bookRepository.findById("1")).thenReturn(Optional.of(checkoutBook1));
        when(mongoUserDetailsService.getMe(principal)).thenReturn(new MongoUserResponse("1a", "", ""));
        when(checkoutRepository.findByUserIdAndBookId("1a", "1")).thenReturn(checkout1);
        when(bookRepository.save(book1)).thenReturn(book1);
        //GIVEN
        Book actual = bookService.returnBook("1a", "1", principal);
        Book expected = book1;
        //THEN
        verify(bookRepository).findById("1");
        verify(mongoUserDetailsService).getMe(principal);
        verify(checkoutRepository).findByUserIdAndBookId("1a", "1");
        verify(bookRepository).save(book1);
        Assertions.assertEquals(expected, actual);
    }

}