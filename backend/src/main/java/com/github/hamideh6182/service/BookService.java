package com.github.hamideh6182.service;

import com.github.hamideh6182.exception.BookNotFoundException;
import com.github.hamideh6182.exception.UnauthorizedException;
import com.github.hamideh6182.model.Book;
import com.github.hamideh6182.model.BookRequest;
import com.github.hamideh6182.model.Checkout;
import com.github.hamideh6182.repository.BookRepository;
import com.github.hamideh6182.repository.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final IdService idService;
    private final PhotoService photoService;
    private final MongoUserDetailsService mongoUserDetailsService;
    private final CheckoutRepository checkoutRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Book addBook(BookRequest bookRequest, MultipartFile photo, Principal principal) {
        if (bookRequest.title() == null) {
            throw new IllegalArgumentException("missing title");
        }
        String photoUri;
        if (photo != null) {
            try {
                photoUri = photoService.uploadPhoto(photo);
            } catch (IOException e) {
                throw new InputMismatchException("The photo upload didn't work: " + e.getMessage());
            }
        } else {
            photoUri = null;
        }
        String id = idService.generateId();
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Book newBook = new Book(
                id,
                bookRequest.title(),
                bookRequest.author(),
                bookRequest.description(),
                bookRequest.copies(),
                bookRequest.copies(),
                bookRequest.category(),
                photoUri,
                adminId
        );
        return bookRepository.save(newBook);
    }

    public Book deleteBook(String id, Principal principal) {
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        if (!book.get().userId().equals(adminId)) {
            throw new UnauthorizedException("Administration page");
        }
        bookRepository.delete(book.get());
        return book.get();
    }

    public Book increaseBookQuantity(String id, Principal principal) {
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        if (!book.get().userId().equals(adminId)) {
            throw new UnauthorizedException("Administration page only");
        }
        int newCopies = book.get().copies() + 1;
        int newCopiesAvailable = book.get().copiesAvailable() + 1;
        Book newBook = new Book(
                id,
                book.get().title(),
                book.get().author(),
                book.get().description(),
                newCopies,
                newCopiesAvailable,
                book.get().category(),
                book.get().img(),
                adminId
        );
        return bookRepository.save(newBook);
    }

    public Book decreaseBookQuantity(String id, Principal principal) {
        String adminId = mongoUserDetailsService.getMe(principal).id();
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty() || book.get().copies() <= 0 || book.get().copiesAvailable() <= 0) {
            throw new BookNotFoundException("Book not found or quantity locked");
        }
        if (!book.get().userId().equals(adminId)) {
            throw new UnauthorizedException("Only Administration");
        }
        int newCopies = book.get().copies() - 1;
        int newCopiesAvailable = book.get().copiesAvailable() - 1;
        Book newBook = new Book(
                id,
                book.get().title(),
                book.get().author(),
                book.get().description(),
                newCopies,
                newCopiesAvailable,
                book.get().category(),
                book.get().img(),
                adminId
        );
        return bookRepository.save(newBook);
    }

    public Book checkoutBook(String userId, String bookId, Principal principal) {
        String userOrAdminId = mongoUserDetailsService.getMe(principal).id();
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserIdAndBookId(userId, bookId);

        if (book.isEmpty() || validateCheckout != null || book.get().copiesAvailable() <= 0) {
            throw new BookNotFoundException("Book doesn't exist or already checked out by user");
        }

        int newCopiesAvailable = book.get().copiesAvailable() - 1;
        Book newBook = new Book(
                bookId,
                book.get().title(),
                book.get().author(),
                book.get().description(),
                book.get().copies(),
                newCopiesAvailable,
                book.get().category(),
                book.get().img(),
                userOrAdminId
        );

        bookRepository.save(newBook);

        String checkoutId = idService.generateId();
        Checkout checkout = new Checkout(
                checkoutId,
                newBook.id(),
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                userId
        );

        checkoutRepository.save(checkout);

        return newBook;
    }

    public boolean checkoutBookByUser(String userId, String bookId) {
        return checkoutRepository.findByUserIdAndBookId(userId, bookId) != null;
    }

    public Book returnBook(String userId, String bookId, Principal principal) {
        Optional<Book> book = bookRepository.findById(bookId);
        String userOrAdminId = mongoUserDetailsService.getMe(principal).id();
        Checkout validateCheckout = checkoutRepository.findByUserIdAndBookId(userId, bookId);

        if (book.isEmpty() || validateCheckout == null) {
            throw new BookNotFoundException("Book does not exist or not checked out by user");
        }

        int newCopiesAvailable = book.get().copiesAvailable() + 1;
        Book newBook = new Book(
                bookId,
                book.get().title(),
                book.get().author(),
                book.get().description(),
                book.get().copies(),
                newCopiesAvailable,
                book.get().category(),
                book.get().img(),
                userOrAdminId
        );

        bookRepository.save(newBook);
        checkoutRepository.deleteById(validateCheckout.id());
        return newBook;
    }
}
