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
        if (mongoUserDetailsService.getMe(principal) == null) {
            throw new UnauthorizedException("Admin not found or only admin can add the book");
        }
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
        Book newBook = new Book(
                id,
                bookRequest.title(),
                bookRequest.author(),
                bookRequest.description(),
                bookRequest.copies(),
                bookRequest.copies(),
                bookRequest.category(),
                photoUri
        );
        return bookRepository.save(newBook);
    }

    public Book deleteBook(String id, Principal principal) {
        if (mongoUserDetailsService.getMe(principal) == null) {
            throw new UnauthorizedException("Admin not found or only admin can delete the book");
        }
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.delete(book.get());
        return book.get();
    }

    public Book increaseBookQuantity(String id, Principal principal) {
        if (mongoUserDetailsService.getMe(principal) == null) {
            throw new UnauthorizedException("Amin not found or only admin can increase the quantity");
        }
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found");
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
                book.get().img()
        );
        return bookRepository.save(newBook);
    }

    public Book decreaseBookQuantity(String id, Principal principal) {
        if (mongoUserDetailsService.getMe(principal) == null) {
            throw new UnauthorizedException("Admin not found or only admin can decrease the quantity");
        }
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty() || book.get().copies() <= 0 || book.get().copiesAvailable() <= 0) {
            throw new BookNotFoundException("Book not found or quantity locked");
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
                book.get().img()
        );
        return bookRepository.save(newBook);
    }

    public Book checkoutBook(String userId, String bookId, Principal principal) {
        if (mongoUserDetailsService.getMe(principal) == null) {
            throw new UnauthorizedException("User not found");
        }
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
                book.get().img()
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
        if (mongoUserDetailsService.getMe(principal) == null) {
            throw new UnauthorizedException("User not found, or only user or admin can return the book");
        }
        Optional<Book> book = bookRepository.findById(bookId);
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
                book.get().img()
        );

        bookRepository.save(newBook);
        checkoutRepository.deleteById(validateCheckout.id());
        return newBook;
    }
}
